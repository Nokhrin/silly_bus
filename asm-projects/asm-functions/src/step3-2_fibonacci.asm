;fibonacci
;
;fib(n)=fib(n-1)+fib(n-2)
;fib(1)=1
;fib(0)=0
;
;./step3-2_fibonacci; echo $?
;# 55    ; fib(10) = 55


default rel

section .text
global _start

_start:
    ;rax=итог
    ;rbx=результат одного рекурсивного вызова
    ;rdi=аргумент одного рекурсивного вызова
    mov rdi, 10 ; n
    call .fib

.end:
    mov edi, eax
    mov eax, 60
    syscall

.fib:
    ;создание кадра стека
    push rbp ; база в стек
    mov rbp, rsp ; верхушка -> база кадра
    push rbx

    ;условия выхода
    cmp rdi, 1
    je .fib_base_one
    cmp rdi, 0
    je .fib_base_zero

    ;fib(n)=fib(n-1)+fib(n-2)
    ;
    ;первый вызов
    ; fib(n-1)
    push rdi ; сохранить n для второго вызова
    sub rdi, 1 ; n=n-1
    call .fib
    mov rbx, rax  ; сохранить fib(n-1)
    pop rdi ; восстановить n

    ;второй вызов
    ; fib(n-2)
    sub rdi, 2 ; n=n-2
    call .fib
    add rax, rbx ; fib(n-2)+fib(n-1)
    pop rbx
    mov rsp, rbp ; родительская верхушка
    pop rbp ; родительская база, rsp+=8
    ret

.fib_base_one:
    mov rsp, rbp ; родительская база
    pop rbp ; адрес возврата - rip
    mov eax, 1 ; fib(1)=1
    ret
.fib_base_zero:
    mov rsp, rbp ; родительская база
    pop rbp ; адрес возврата - rip
    mov eax, 0 ; fib(0)=0
    ret

