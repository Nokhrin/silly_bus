; Вернуть ascii представление int
;
; nasm -f elf64 -g src/step4_demo2_int2ascii.asm -o step4_demo2_int2ascii.o
; ld -no-pie -m elf_x86_64 step4_demo2_int2ascii.o -o step4_demo2_int2ascii
default rel
global _start
section .bss
    digit resb 1
section .data
    newline db 10 ; "\n"

section .text

_start:
    mov al,9 ; 9==0x9 -> al=0x09
    add al,'0' ; '0'==0x30 -> al=0x09+0x30=0x39 == 57 == '9'
    mov [digit], al

.print_digit:
    mov al, 1
    mov rdi, 1
    lea rsi, [digit]
    mov rdx, 1
    syscall

.print_newline:
    mov al, 1
    mov rdi, 1
    lea rsi, [newline]
    mov rdx, 1
    syscall

_finish:
    xor dil, dil
    mov al, 0xe7
    syscall


; ./step4_demo2_int2ascii  # Ожидание: вывод "9\n"