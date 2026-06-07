; lib_string
default rel

section .text
global strlen
global memcpy
global strcpy
global strncpy
global safecpy

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

strcpy: ; копирование до \0 , границы не проверяются, гарантируется копирование найденного \0
    ; (арг1=адрес_dst, арг2=адрес_src)
    push rbp
    mov rbp, rsp

    sub rsp, 2*8 ; локальный стек

    mov r8, [rbp+16] ; dst
    mov [rbp-1*8], r8 ; dst
    mov rax, r8 ; копия dst для возврата из функции

    mov r9, [rbp+24] ; src
    mov [rbp-2*8], r9 ; src

    .copy_loop:
    mov r8, [rbp-1*8] ; dst адрес
    mov r9, [rbp-2*8] ; src адрес

    mov r10b, byte [r9] ; байт значения из src
    mov byte [r8], r10b ; байт значения в dst

    cmp r10b, 0x00
    je .break_loop

    inc [rbp-2*8] ; адрес src += 1байт
    inc [rbp-1*8] ; адрес dst += 1байт
    jmp .copy_loop

    .break_loop:
    mov rsp, rbp
    pop rbp
    ret

strncpy: ; копирование N байт , запись N - len(источник) нулей
    ; (арг1=адрес_dst, арг2=адрес_src, арг3=макс_длина)
    ret

safecpy: ; копирование до \0
    ; (арг1=адрес_dst, арг2=адрес_src, арг3=макс_длина)
    ret
