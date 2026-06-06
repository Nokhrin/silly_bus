;реализация массивов в .data, .bss, стеке
;
;Входные данные:
 ;- data_array: 1, 2, 3, 4, 5
 ;сумма = 15
 ;- bss_array: 10, 20, 30, 40, 50
 ;сумма = 150
 ;- stack_array: 10, 11, 12, 13, 14
 ;сумма = 60
 ;
 ;nasm -f elf64 src/step2-4_bss_data_stack.asm -o step2-4_bss_data_stack.o
 ;ld -m elf_x86_64  step2-4_bss_data_stack.o -o step2-4_bss_data_stack
 ;Ожидаемый результат:
 ;./step2-4_bss_data_stack; echo $?
 ;225    ; 15 + 150 + 60

default rel

ITEMS_COUNT equ 5

section .bss
    bss_array resb ITEMS_COUNT ; bss_array: 0,0,0,0,0

section .data
    data_array db 1, 2, 3, 4, 5 ; data_array: 1, 2, 3, 4, 5

section .text
global _start

_start:
    xor rax, rax ; итог=сумма значений элементов трех массивов

    lea rbx, [data_array]
    call .process_data_array
    call .process_bss_array
    call .process_stack_array
.end:
    mov rdi, rax
    mov rax, 60
    syscall

.process_data_array:
;- data_array: 1, 2, 3, 4, 5
;сумма = 15
    push rbx
    mov rcx, ITEMS_COUNT
    dec rcx
    .data_loop:
        add al, byte [rbx + rcx]
        dec rcx
        jge .data_loop
    pop rbx
    ret
.process_bss_array:
    push rbx
    mov rcx, 0 ; индекс
    mov rbx, bss_array ; база массива
    ; инициализировать bss_array: 10, 20, 30, 40, 50
    .init_loop:
        mov rdx, rcx ; вычисление элемента
        inc rdx
        mul rdx, 10

        mov [rbx + rcx], rdx

        inc rcx
        cmp rcx, ITEMS_COUNT
        jne .init_loop
    ; суммировать bss_array
    mov rcx, 0
    .sum_loop:
        add al, [rbx + rcx]
        inc rcx
        cmp rcx, ITEMS_COUNT
        jne .sum_loop
    pop rbx
    ret
.process_stack_array:
 ;- stack_array: 10, 11, 12, 13, 14
 ;сумма = 60
    push rbp; кадр стека: 5 * 1 байт
    mov rbp, rsp
    sub rsp, ITEMS_COUNT

    mov rcx, ITEMS_COUNT ; счетчик цикла
    .loop:
        mov r8, rcx ; стек[i] = i + 10
        add r8, 9 ;         sub r8, 1 ; add r8, 10
        add rax, r8
        dec rcx
        jnz .loop

    mov rsp, rbp
    pop rbp
    ret