; доступ к памяти
; nasm -f elf64 -g src/step2_demo1_memory.asm -o step2_demo1_memory.o
; ld -no-pie -m elf_x86_64 step2_demo1_memory.o -o step2_demo1_memory
; ./step2_demo1_memory; echo $?

section .data
    value dd 42 ; 4 bytes 0x002A initiated

section .bss
    storage resq 1 ; 8 bytes 0x00000000 reserved

section .text
global _start

_start:
    mov eax, [value] ; поместить dd (32 битное) значение в регистр eax из метки value
    mov qword [storage], rax ; инициализировать 64битным значением из rax значение storage
    
    mov rdi, rax
    mov rax, 231
    syscall