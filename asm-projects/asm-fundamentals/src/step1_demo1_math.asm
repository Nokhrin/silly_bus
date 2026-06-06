; арифметика
section .text
global _start

_start:
    mov rax, 7
    mov rbx, 3
    imul rax, rbx
    mov rcx, 2
    xor rdx, rdx ; rdx=0
    idiv rcx ; rdx:rax=(0x0000)(0x0111)=0x00000111 DIV 0x00000010
    mov rdi, rax
    mov rax, 231
    syscall