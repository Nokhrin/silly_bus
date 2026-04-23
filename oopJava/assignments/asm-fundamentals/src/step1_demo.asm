; imul, idiv
; nasm -f elf64 -g src/step1_demo.asm -o step1_demo.o
; ld -no-pie -m elf_x86_64 step1_demo.o -o step1_demo
; gdb -batch -ex 'run' -ex 'quit' ./step1_demo; echo $?
section .text
global _start

_start:
    mov rax, 7 ; number a
    mov rbx, 3 ; number b
    imul rax, rbx ; rax *= rbx = 21
    mov rcx, 2 ; num d
    mov rdx, 0 ; reset rdx - for idiv
    idiv rcx ; rdx / rcx => rax=10,rdx=1

    mov rdi, rax ; code 10
    mov rax, 231 ; exit_group
    syscall

