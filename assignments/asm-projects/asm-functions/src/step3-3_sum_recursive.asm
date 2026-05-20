;recursion depth
;sum_recursive
;n + sum(n-1)
;
;# Тест 1: n = 0 (базовый случай)
;VALUE equ 0
;./step_test; echo $?    # Ожидание: 0
;
;# Тест 2: n = 1 (базовый случай)
;VALUE equ 1
;./step_test; echo $?    # Ожидание: 1
;
;# Тест 3: n = 5 (рекурсия)
;VALUE equ 5
;./step_test; echo $?    # Ожидание: 15 (5+4+3+2+1+0)
;
;# Тест 4: n = 22 (глубокая рекурсия)
;VALUE equ 22
;./step_test; echo $?    # Ожидание: 253 (22*23/2)
;
;# Тест 5: n = -1 (ошибка параметра)
;VALUE equ -1
;./step_test; echo $?    # Ожидание: 2 (EXIT_INVALID_ARG)

default rel

SYS_EXIT equ 60 ;
EXIT_SUCCESS equ 0
EXIT_INVALID_ARG equ 2
EXIT_STACK_OVERFLOW equ 3

;VALUE equ -1;
;VALUE equ 0;
;VALUE equ 1;
;VALUE equ 5;
;VALUE equ 22;
VALUE equ 44;
SAFE_STACK_MARGIN equ 0x400 ; 1024B

; потребление стека на один рекурсивный кадр:
; push rbp        → 8 байт (сохранение base pointer)
; push rdi        → 8 байт (сохранение аргумента)
; call            → 8 байт (адрес возврата)
; ИТОГО: 24 байта на кадр

; максимальная глубина без переполнения:
; SAFE_STACK_MARGIN / размер_кадра = 0x400 / 0x18 = 42 кадра
; переполнение сработает на вызове №43 (43 × 24 = 1032 > 1024)

; для демонстрации переполнения:
; VALUE equ 44  →  44 кадра × 24 байта = 1056 байт > 1024 байт


section .text
global _start

_start:
    mov rdi, VALUE ; n

    ;[Валидация аргумента]
    cmp rdi, 0 ; if n < 0
    jl .param_error

    xor rax, rax ; total
    mov r12, rsp ; адрес стека после загрузки программы для валидации границ стека в рекурсивных вызовах
    ; sub rsp, 8 ; выравнивание не требуется, так как по abi "It is guaranteed to be 16-byte aligned at process entry"

    call .sum_recursive ; push rip+16 неявно


.end:
    mov edi, eax
    mov eax, SYS_EXIT
    syscall



.sum_recursive:
    ;контроль диапазона адресов стека
    mov r10, r12 ; if (initial - current) > SAFE_STACK_MARGIN
    sub r10, rsp
    cmp r10, SAFE_STACK_MARGIN
    ja .overflow_error

    ;база
    cmp rdi, 1 ; if n == 1
    je .base1

    cmp rdi, 0 ; if n == 0
    je .base0

    ;кадр стека - пролог
    push rbp        ; размер_кадра+=8Б
    mov rbp, rsp

    ;рекурсия
    push rdi ; сохранить n для рекурсивного вызова ; размер_кадра+=8Б
    dec rdi ; n-=1
    call .sum_recursive ; размер_кадра+=8Б

    pop rdi
    add rax, rdi ; total+=n

.epilogue:    ;кадр стека - эпилог
    mov rsp, rbp
    pop rbp
    ret

    ;размер_кадра итого = 24Б = 0x18
    ;0x400/0x18=42 -> при пороге 1024Б переполнение произойдет на вызове №43


.base1:
    mov eax, 1
    ret

.base0:
    xor eax, eax
    ret

.param_error:
    mov eax, EXIT_INVALID_ARG
    jmp .end

.overflow_error:
    mov eax, EXIT_STACK_OVERFLOW
    jmp .epilogue