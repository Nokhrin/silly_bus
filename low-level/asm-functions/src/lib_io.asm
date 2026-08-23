;io_wrappers

default rel
SYS_EXIT equ 231
SYS_READ equ 0
SYS_WRITE equ 1

LINE_LEN_MAX equ 64

section .bss
    line_buffer resb LINE_LEN_MAX + 1 ; +1 для \0

section .data
    msg_prompt db "Enter: "
    msg_prompt_len equ $ - msg_prompt

    msg_result db "Echo: "
    msg_result_len equ $ - msg_result

    new_line db 0x0a
    new_line_len equ $ - new_line

section .text
    global write_line
    global read_line
    global string_of
    global ascii_to_int

write_line: ; буфер -> stdout
    push rbp
    mov rbp, rsp

    mov rax, SYS_WRITE ; код syscall
    mov rdi, 1        ; arg 1 == stdout
    mov rsi, [rbp+16] ; arg 2 == адрес буфера
    mov rdx, [rbp+24] ; arg 3 == количество байт
    syscall

    mov rsp, rbp
    pop rbp
    ret ; количество записанных байт в rax

read_line: ; stdin -> буфер
    push rbp
    mov rbp, rsp

    mov rax, SYS_READ ; код syscall
    mov rdi, 0 ; arg 1 == stdin
    mov rsi, [rbp+16] ; arg 2 == адрес буфера
    mov rdx, [rbp+24] ; arg 3 == количество байт
    syscall

    cmp rax, 0 ; rax = количество байт | -errno
    jle .read_line_exit

    .read_line_exit:
    mov rsp, rbp
    pop rbp
    ret ; значение в rax

string_of: ; int -> str
    push rbp
    mov rbp, rsp

    mov rax, [rbp+16] ; арг1: число для конвертации
    mov r8, [rbp+24] ; арг2: адрес буфера
    mov r9, [rbp+32]  ; арг3: размер буфера
    lea rsi, [r8+r9-1] ; указатель на последний байт буфера, -1 для \n

    mov r10, 0 ; счетчик цифр
    mov r11, 10 ; делитель

    cmp rax, 0
    jnz .string_of_convert_loop
    mov [rsi], 0x30
    mov r10, 1
    jmp .return_string

    .string_of_convert_loop:
        xor rdx, rdx
        div r11 ; rax - частное, rdx - остаток
        add rdx, 0x30 ; остаток-число -> остаток-asciiкод
        dec rsi ; указатель цифры -=1

        cmp rsi, r8 ; выход за границу буфера
        jb .return_string

        mov [rsi], dl ; запись ascii кода цифры
        inc r10 ; счетчик цифр +=1

        cmp rax, 0 ; частное==0
        jnz .string_of_convert_loop

    .return_string:
    mov rax, rsi ; адрес
    mov rdx, r10 ; длина
    mov rsp, rbp
    pop rbp
    ret

ascii_to_int:
    push rbp
    mov rbp, rsp

    mov r9, [rbp+24]; длина значения
    mov r8, [rbp+16]; адрес буфера
    xor rax, rax ; число

    .build_num:
        cmp r9, 0
        jz .build_num_done
        movzx r10d, byte [r8] ; байт по адресу r8
        cmp r10b, '0'
        jb .build_num_done ; не цифра
        cmp r10b, '9'
        ja .build_num_done ; не цифра
        sub r10b, 0x30 ; ascii-код -> значение
        imul rax, 10 ; следующий разряд
        add rax, r10
        inc r8
        dec r9
        jmp .build_num

    .build_num_done:
    mov rsp, rbp
    pop rbp
    ret
