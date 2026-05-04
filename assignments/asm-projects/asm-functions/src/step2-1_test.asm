;Обработать массив через caller-saved регистры
;
;Входные данные:
 ;- array = [10, 20, 30, 40, 50]
 ;- длина = 5
 ;
 ;Ожидаемый результат:
 ;./step2-1_array_data; echo $?
 ;150
;
default rel

section .data
    array dd 10, 20, 30, 40, 50 ; dd - 4 байта на элемент -> 5*4 = 20 байт на массив
    ITEM_BYTES equ 4
    ARRAY_LEN equ ($ - array) / ITEM_BYTES

section .text
global _start

_start:
    lea rbx, [array]  ;передать адрес массива в функцию sum_array
    mov rbp, ARRAY_LEN ;передать длину массива в функцию sum_array
    call .sum_array

.end:
    mov rdi, rax
    mov al, 60
    syscall

.sum_array:
    xor rax, rax ; total=0
    mov rax, [rbx + rcx*1] ; total=array[0]
    mov rcx, 1 ; i=1
    .sum_loop:
        add rax, [rbx + rcx*ITEM_BYTES]
        inc rcx
        cmp rcx, ARRAY_LEN
        jnz .sum_loop
    ret