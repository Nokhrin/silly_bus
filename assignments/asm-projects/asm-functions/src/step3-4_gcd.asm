;нод
;gcd(a, b) = gcd(b, a mod b)
;gcd(a, 0) = a

default rel

EXIT_INVALID_ARG equ 2
EXIT_STACK_OVERFLOW equ 3

section .text
global _start

_start:


;=== ТЕСТЫ ===

;gcd(48, 18) = 6
    mov edi, 48 ; arg_1
    mov esi, 18 ; arg_2
    SAFE_STACK_MARGIN equ 0x400 ; 1024B

; SO
;gcd(13, 8) -> 3 (код ошибки переполнения стека)
;    mov edi, 13 ; arg_1
;    mov esi, 8 ; arg_2
;    SAFE_STACK_MARGIN equ 0x40 ; 64B

; a=0 -> gcd(0, b)=b
;gcd(0, 18) -> 18
;    mov edi, 0 ; arg_1
;    mov esi, 18 ; arg_2
;    SAFE_STACK_MARGIN equ 0x400 ; 1024B

; a<0
;gcd(-48, 18) -> 6
;    mov edi, -48 ; arg_1
;    mov esi, 18 ; arg_2
;    SAFE_STACK_MARGIN equ 0x400 ; 1024B

; b<0
;gcd(48, -18) -> 6
;    mov edi, 48 ; arg_1
;    mov esi, -18 ; arg_2
;    SAFE_STACK_MARGIN equ 0x400 ; 1024B

    mov r12, rsp; первый адрес стека для контроля переполнения

    call .rec ;  global_mem += 8Б

.end:
    mov edi, eax
    mov eax, 60
    syscall

.base:  ; gcd(a, 0)=a
    mov eax, esi ; при нулевом остатке результат равен текущему делителю (== esi)
    jmp .epilogue

.rec: ; gcd(a,b)=gcd(b, a mod b)
    .prologue: ;  создание локального стека .rec
    push rbp ; база кадра стека ; local_mem += 8Б
    mov rbp, rsp ; вершина кадра стека
    ;проверка размера кадра стека
     ;(gdb) r
     ;(gdb) set $initial_rsp = $rsp
     ;проверять смещение
     ;(gdb) p/x (long)$initial_rsp - (long)$rsp

    .stack_overflow_check:
    mov r10, r12 ; адрес_стека_при_загрузке - адрес_вершины
    sub r10, rsp
    cmp r10, SAFE_STACK_MARGIN
    jge .err_stack_overflow

    .div_by_zero_check:
    cmp esi, 0
    je .err_illegal_arg

    .absA:
    test edi, edi ; a<0 -> SF!=0
    jns .positiveA
    neg edi ; |a|
    .positiveA:

    .absB:
    test esi, esi ; b<0 -> SF!=0
    jns .positiveB
    neg esi ; |b|
    .positiveB:

    mov eax, edi  ; a
    xor edx, edx ; обнулить для остатка
    div esi ; eax/esi=a/b => EAX ← Quotient, EDX ← Remainder

    cmp edx, 0 ; a mod b == 0
    je .base

    mov edi, esi ; arg_2 -> arg_1
    mov esi, edx ; remainder -> arg_2

    call .rec  ; local_mem += 8Б

    .epilogue: ; уничтожение локального стека .rec == возврат границ кадра стека-предшественника
    mov rsp, rbp ; вершина кадра стека
    pop rbp ; база кадра стека

    ret

    ; размер кадра стека = смещения, вызванные push и call = 16Б
    ; для порога 64Б переполнение произойдет на вызове №5

.err_illegal_arg:
    mov eax, EXIT_INVALID_ARG
    jmp .epilogue

.err_stack_overflow:
    mov eax, EXIT_STACK_OVERFLOW
    jmp .epilogue