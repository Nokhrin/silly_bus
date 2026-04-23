; (x * y) - (z / w)
; x=9, y=4, z=17, w=5
; src/step1_task.asm

section .text
global _start

_start:
    mov rax, 17 ; z
    mov rcx, 5 ; w
    div rcx ; rax=3, rdx=2
    mov rcx, rax ; save 3

    mov rax, 9 ; x
    mov rbx, 4 ; y
    mul rbx ; rax=9*4=36
    
    sub rax, rcx ; rax-=rcx

    mov rdi, rax;
    mov rax, 231
    syscall
