;цикл ввода/вывода
;nasm -f elf64 -g main.asm -o main.o
;ld -no-pie -m elf_x86_64 main.o -o fastlog
section .bss
    buf resb 4096
    line_cnt resq 1
    byte_cnt resq 1

section .text
global _start
_start:
    mov qword [line_cnt], 0
    mov qword [byte_cnt], 0

.read_loop:
    mov rax, 0
    mov rdi, 0
    mov rsi, buf
    mov rdx, 4096
    syscall

    cmp rax, 0
    jle .exit_loop ; ZF=1 | SF<>OF

    mov r10, rax
    add qword [byte_cnt], rax

    lea rcx, [buf]
    mov r11, rax

.count_new_line:
    cmp byte [rcx], 10
    jne .next_char ; ZF=0
    inc qword [line_cnt]

.next_char:
    inc rcx
    dec r11
    jnz .count_new_line

    ; запись в stdout
    mov rax, 1
    mov rdi, 1
    lea rsi, [buf]
    mov rdx, r10
    syscall

    jmp .read_loop

.exit_loop:
    mov rax, 1
    mov rdi, 1
    lea rsi, [.msg_done]
    mov rdx, 5
    syscall

    mov rax, 231
    xor rdi, rdi
    syscall

.msg_done: db "Done", 10