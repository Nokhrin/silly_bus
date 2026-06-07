default rel
SYS_EXIT equ 231
SYS_READ equ 0
SYS_WRITE equ 1

section .bss
section .data
section .rodata
section .text
global _start

_start:
.exit:
    mov eax, SYS_EXIT
    syscall
