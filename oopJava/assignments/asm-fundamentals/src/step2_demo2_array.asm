; доступ к элементу массива
; nasm -f elf64 -g src/step2_demo2_array.asm -o step2_demo2_array.o
; ld -no-pie -m elf_x86_64 step2_demo2_array.o -o step2_demo2_array
; ./step2_demo2_array; echo $?

section .data
    array dd 10, 20, 30, 40, 50

section .text
global _start

_start:
    lea rsi, [array] ; адрес начала массива -> rsi
    mov r8, 2 ; индекс элемента -> array[2]
    mov eax, [rsi + 4*r8] ; смещение от начала массива, 4 байта (dd) на индекс

    mov rdi, rax
    mov rax, 231
    syscall