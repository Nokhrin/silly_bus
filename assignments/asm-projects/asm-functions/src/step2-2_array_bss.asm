;Заполнить массив через callee-saved регистры. Вернуть сумму элементов
;
;Входные данные:
 ;- buffer (9 элементов, .bss)
 ;- заполнение: 10, 20, 30, ...
 ;
 ;./step2-2_array_bss; echo $?
;150    ; 10+20+30+...
;
default rel

BOUNDARY equ 5

section .bss
    buffer resb 5

section .text
    global _start

_start: ;функция main():
    mov rdi, buffer  ;    передать адрес buffer в функцию fill_buffer
    mov rsi, BOUNDARY   ;    передать длину 10 в функцию fill_buffer

    mov rbx, 0x12345678 ; для проверки неизменения значения функцией
    mov r12, 0xabcdef01 ; для проверки неизменения значения функцией

    call .fill_buffer ;    вызвать fill_buffer

    call .sum_buffer ;    вызвать sum_buffer (вернёт сумму)

    mov edi, eax ;    завершить с кодом суммы
    mov eax, 60
    syscall

.fill_buffer: ;функция fill_buffer(адрес, длина):
    push rbx;    сохранить callee-saved RBX
    push r12;    сохранить callee-saved R12
    mov rcx, 0 ; i=0

    .fill_loop: ;    для i от 0 до длина-1: buffer[i] = (i + 1) * 10
        mov r10, rcx
        add r10, 1
        imul r10, 10
        mov [rdi + rcx], r10b
        inc rcx
        cmp rsi, rcx
        jne .fill_loop

    pop rbx;    восстановить callee-saved RBX
    pop r12;    восстановить callee-saved R12
    ret ;    вернуть

.sum_buffer:    ;функция sum_buffer(адрес, длина):
    push rbx;    сохранить callee-saved RBX
    push r12;    сохранить callee-saved R12
    xor rax, rax ;    аккумулятор = 0
    mov rcx, 0 ; i=0
    .sum_loop:  ;    для i от 0 до длина-1:
        movzx ebx, byte [rdi + rcx] ; используется для проверки изоляции значения вызывающей функции
        add eax, ebx    ;        аккумулятор += buffer[i]
        inc rcx
        cmp rsi, rcx
        jnz .sum_loop
    pop rbx;    восстановить callee-saved RBX
    pop r12;    восстановить callee-saved R12
    ret ;    вернуть аккумулятор