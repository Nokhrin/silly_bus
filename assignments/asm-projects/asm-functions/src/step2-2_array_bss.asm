;Заполнить массив через callee-saved регистры. Вернуть сумму элементов
;
;Входные данные:
 ;- buffer (10 элементов, .bss)
 ;- заполнение: 10, 20, 30, ..., 100
 ;
 ;./step2-2_array_bss; echo $?
;550    ; 10+20+30+...+100
;
default rel

BOUNDARY equ 10

section .bss
    buffer resb 10

section .text
    global _start

_start: ;функция main():
    mov rdi, buffer  ;    передать адрес buffer в функцию fill_buffer
    mov rsi, BOUNDARY   ;    передать длину 10 в функцию fill_buffer

    mov rbx, 0x12345678 ; для проверки неизменения значения функцией
    mov r12, 0xabcdef01 ; для проверки неизменения значения функцией

;(gdb) p $rbx
 ;$2 = 0
 ;(gdb) p $r12
 ;$3 = 0
;(gdb) x/10db &buffer
 ;0x402000 <buffer>:	0	0	0	0	0	0	0	0
 ;0x402008:	0	0

    call .fill_buffer ;    вызвать fill_buffer

;(gdb) p/x $rbx
 ;$2 = 0x12345678
 ;(gdb) p/x $r12
 ;$3 = 0xabcdef01
;(gdb) x/10db &buffer
 ;0x402000 <buffer>:	10	20	30	40	50	60	70	80
 ;0x402008:	90	100

    call .sum_buffer ;    вызвать sum_buffer (вернёт сумму)

;(gdb) p/x $rbx
 ;$1 = 0x12345678
 ;(gdb) p/x $r12
 ;$2 = 0xabcdef01
 ;(gdb) p/x $rax
 ;$3 = 0x226

    mov edi, eax ;    завершить с кодом суммы
    mov eax, 60
    syscall

.fill_buffer: ;функция fill_buffer(адрес, длина):
    push rbx;    сохранить callee-saved RBX
    push r12;    сохранить callee-saved R12
    mov rcx, 0 ; i=0

    ; проверка rbx, r12
    ;(gdb) i registers rbx r12
         ;rbx            0x12345678          305419896
         ;r12            0xabcdef01          2882400001

    ; проверка стека
    ;(gdb) x/2xg $rsp
         ;0x7fffffffd918:	0x00000000abcdef01	0x0000000012345678

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
    ; ожидаемое значение ebx=100, rax=550
    ;(gdb) p/d $rax
         ;$2 = 550
         ;(gdb) p/d $ebx
         ;$3 = 100
    pop rbx;    восстановить callee-saved RBX
    pop r12;    восстановить callee-saved R12
    ret ;    вернуть аккумулятор