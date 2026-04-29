; Печать строки в stdout
;
; nasm -f elf64 -g src/step4_demo2_int2ascii.asm -o step4_demo2_int2ascii.o
; ld -no-pie -m elf_x86_64 step4_demo2_int2ascii.o -o step4_demo2_int2ascii
default rel
global _start
global _finish

section .data
    msg db 'hi', 10 ; "hi\n"

section .text

_start:
    lea rsi, [msg] ; буфер для sys_write - первый байт
    mov rdx, 0x3 ; счетчик для sys_write - количество байт для считывания
    mov rdi, 0x1 ; файловый дескриптор stdout для sys_write
    mov rax, 0x1 ; код системного вызова sys_write

    ; === БЛОК 5: Вычисления ===
    ;
    ; === ТОЧКА ОСТАНОВА ===
    ;

    syscall

.end:
    xor dil, dil
    mov al, 0xe7
    syscall


; ./step4_demo2_int2ascii  # Ожидание: вывод "9\n"