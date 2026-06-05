; lib_string
default rel

section .text
global strlen
global memcpy

strlen: ; strlen(арг1=адрес_первого_байта_значения)
    push rbp
    mov rbp, rsp

    sub rsp, 2*8 ; 16 байт в локальном стеке: указатель, счетчик
    mov r8, [rbp+2*8] ; арг1=адрес_первого_байта_значения
    mov [rbp-1*8], r8 ; указатель (1я переменная в лок стеке)
    mov byte [rbp-2*8], 0 ; счетчик (2я переменная в лок стеке)

    .strlen_loop:
    cmp byte [r8], 0 ; текущий байт == 0 ? (EOF?)
    je .strlen_loop_break
    inc r8 ; указатель += 1
    mov [rbp-1*8], r8
    inc [rbp-2*8] ; счетчик += 1
    jmp .strlen_loop
    .strlen_loop_break:
    mov rax, [rbp-2*8]

    mov rsp, rbp
    pop rbp
    ret ; rax == счетчик (значение)

;Циклическое копирование N байт из источника в приёмник
memcpy: ;арг1=адрес_приемника, арг2=адрес_источника, арг3=количество_байт
    push rbp
    mov rbp, rsp

    ;sub rsp, 1*8 ; выделить 8 байт стека:
    ;rbp=rsp
    ;rsp=rsp-8
    ;
    ;===начало выделенной области
    ;rbp==rsp+8
    ;rsp-1
    ;...
    ;rsp-0
    ;===конец выделенной области
    ;
    ;модель
    ;>>> rsp=rbp=64
    ;>>> rsp-=8
    ;>>> rbp,rsp
    ;>>> [rbp for i in range(8)]
    ;[64, 64, 64, 64, 64, 64, 64, 64]
    ;>>> [rsp+(8-i) for i in range(8)]
    ;[64, 63, 62, 61, 60, 59, 58, 57]
    ;

    sub rsp, 1*8 ; выделить 8 байт стека:
    mov byte [rbp-8], 0 ; i == счетчик байт

;call + локальный push -> rbp-16
;=>
;арг1=адрес_приемника=rbp+16
;арг2=адрес_источника=rbp+24
;арг3=количество_байт=rbp+32
    .memcpy_loop:
    mov r8, [rbp-8] ; r8 == счетчик/смещение
    cmp r8, [rbp+32]
    je .memcpy_loop_break
    mov r9, [rbp+24] ; адрес источника
    mov r10, [rbp+16] ; адрес приемника

    mov r11, [r9+r8]; прочитать байт источника
    mov [r10+r8], r11 ; записать байт в приемник
    inc [rbp-8]
    jmp .memcpy_loop

    .memcpy_loop_break:

    mov rax, [rbp+16]
    mov rsp, rbp
    pop rbp
    ret ; адрес_приемника