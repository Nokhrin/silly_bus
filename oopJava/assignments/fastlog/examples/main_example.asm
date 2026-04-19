;цикл ввода/вывода
;nasm -f elf64 -g main_example.asm -o main.o
;ld -no-pie -m elf_x86_64 main.o -o fastlog_test
section .bss
    buf resb 4096

section .text
global _start
_start:
    ; чтение stdin - syscall(read)
    mov rax, 0 ; sys_read
    mov rdi, 0 ; stdin / fd 0
    mov rsi, buf
    mov rdx, 4096
    syscall

    ; проверка
    cmp rax, 0
    jle .exit

    mov r10, rax

    ; запись в stdout - syscall(write)
    mov rax, 1 ; sys_write
    mov rdi, 1 ; stdout / fd 1
    mov rsi, buf
    mov rdx, r10
    syscall

    ; повтор
    jmp _start

.exit:
    mov rax, 60 ; sys_exit_group
    xor rdi, rdi
    syscall