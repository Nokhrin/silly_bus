; возвращает цифру по ее ascii коду
;
; step4_demo1_ascii2int.asm — конверсия одного символа
; nasm -f elf64 -g src/step4_demo1_ascii2int.asm -o step4_demo1_ascii2int.o
; ld -no-pie -m elf_x86_64 step4_demo1_ascii2int.o -o step4_demo1_ascii2int
; ./step4_demo1_ascii2int; echo $?  # Ожидание: 7 (ASCII '7' = 0x37)

section .bss
section .data
section .text
global _start
_start:
    mov al, '7' ;
    sub al, '0' ; '7'-'0' > 0x37-0x30 > 0x7
    movzx eax, al
_end:
    mov edi, eax
    mov eax, 231
    syscall

; ./step4_demo_ascii2int; echo $?  # Ожидание: 7