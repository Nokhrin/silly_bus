;int2ascii
;
;Конвертирует целое число в ascii строку
;выводит сформированную ascii строку в stdout

default rel

section .bss
    string_buffer resb 8 ; строка 7 символов + \0

section .text
global _start
global int2ascii

_start:
;    mov edi, 120; n
    mov edi, 9999999; n
    call int2ascii

.syscall_write:
    mov eax, 1 ; sys_write
    mov edi, 1 ; stdout
    syscall

.exit:
    mov eax, 60 ; sys_exit
    xor edi, edi ; status 0
    syscall

int2ascii:
    ; Вход: RDI = число для конверсии
    ; Выход: RSI = адрес буфера, RDX = длина строки
    cmp edi, 0

    lea rsi, [string_buffer+7] ; адрес буфера
    ; последний байт буфера, базовый случай
    mov byte [rsi], 0 ; \0
    mov r8d, 0 ; счетчик цифр

    mov r9d, 10 ; делитель - основание системы

.convert_loop:
    xor edx, edx
    mov eax, edi ; n в делимое
    div r9d ; eax - целая часть, edx - остаток

    add edx, 0x30 ; конверсия остатка в ascii
    dec rsi ; символ в буфер
    mov [rsi], dl

    inc r8d ; счетчик цифр += 1

    mov edi, eax ; остаток -> число для конверсии
    cmp eax, 0
    jg .convert_loop

    mov edx, r8d ; вернуть длину строки
    ret
