;Факториал
;
;./step3-1_factorial; echo $?
;# 120    ; factorial(5) = 5*4*3*2*1 = 120
;Тестирование - граничные значения (0, 1, 12)

;factorial(n)=n*factorial(n-1) ; call -> кадр стека
;factorial(1)=1 ; условие выхода


default rel
section .text
global _start

_start:
    mov dil, 5 ; арг1 по abi ; 8bit
    call .factorial

.end:
    movzx edi, al ; запись в 32бит регистр обнуляет старшие 32 бита
    mov eax, 60 ; sys_exit
    syscall

.factorial:
    ; вход dil=n
    ; выход ax=n!

    push rbp; кадр стека - сохранить базовый указатель
    mov rbp,rsp; ; кадр стека - обновить базовый указатель

    cmp dil, 1 ; if n==1
    je .base_case

    push rdi ; 64bit -> выравнивание стека
    dec dil
    call .factorial
    pop rdi
    mov bl, dil
    mul bl ; ax=al*bl

    mov rsp,rbp; ; кадр стека - обновить вершину
    pop rbp; кадр стека - восстановить базовый указатель

    ret

.base_case:
    mov rsp,rbp; ; кадр стека - обновить вершину
    pop rbp; кадр стека - восстановить базовый указатель

    mov al, 1 ; factorial(1)=1
    ret
