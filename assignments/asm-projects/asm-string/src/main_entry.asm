;main_entry
;копирование заголовка и полезной нагрузки в общий буфер, вычисление итоговой длины
;nasm -f elf64 src/lib_string.asm -o lib.o
;nasm -f elf64 src/main_entry.asm -o main.o
;ld lib.o main.o -o asm_string_app
default rel
SYS_EXIT equ 231
EXIT_SUCCESS equ 0
EXIT_MISMATCH equ 1

extern memcpy
extern strlen

section .bss
    work_buf resb 32
section .data
    ; строка нулевой длины
;    test_str db "", 0x00
;    test_str_len equ $ - test_str
    ; strlen -> 0

    ; минимальная ненулевая
;    test_str db "s", 0x00
;    test_str_len equ $ - test_str
    ; strlen -> 1

    ; остановка на null-term
;    test_str db "read", 0x00, "skipped"
;    test_str_len equ $ - test_str
    ; strlen -> 4

    ; корректная строка
    test_str db "hello asm", 0x00
    test_str_len equ $ - test_str
    ; strlen -> 9

section .text
global _start
_start:
    ; тест - .bss неинициализирован, остановка на первом символе (0x00)
    ; strlen -> 0
;     push test_str_len-1 ; арг3 = test_str_len
;     push test_str ; арг2 = test_str
;     push work_buf+1 ; арг1 = work_buf

    ; прод
    push test_str_len ; арг3 = test_str_len
    push test_str ; арг2 = test_str
    push work_buf ; арг1 = work_buf

    call memcpy ; memcpy(арг1=адрес_приемника, арг2=адрес_источника, арг3=количество_байт) -> адрес приемника в rax
    add rsp, 3*8

    push work_buf ; арг1=адрес_первого_байта_значения
    call strlen ; strlen(арг1=адрес_первого_байта_значения) -> rax==итоговое смещение

.exit:
    mov rdi, rax
    mov eax, SYS_EXIT
    syscall
