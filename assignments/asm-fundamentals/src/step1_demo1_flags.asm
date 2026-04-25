; flags
section .text
global _start

_start:
    mov rax, 10
    mov rbx, 10
    sub rax, rbx ; cf=0, zf=1, sf=0, of=0
    mov rax, 5
    mov rbx, 10
    sub rax, rbx ; cf=0, zf=0, sf=1, of=0

    xor rdi, rdi
    mov rax, 231
    syscall