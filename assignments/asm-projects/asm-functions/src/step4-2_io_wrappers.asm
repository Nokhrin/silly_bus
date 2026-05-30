;io_wrappers

default rel
SYS_EXIT equ 231
SYS_READ equ 0
SYS_WRITE equ 1

LINE_LEN_MAX equ 64
INPUT_NUM equ 3628800

section .bss
    line_buffer resb LINE_LEN_MAX + 1 ; +1 для \0

section .data
    msg_prompt db "Enter: "
    msg_prompt_len equ $ - msg_prompt

section .text
global _start

_start:
    push msg_prompt_len ; длина значения
    push msg_prompt ; адрес значения
    call .write_line ; строка в stdout
    add rsp, 16 ; "удалить" значения размера буфера, адреса буфера

    push LINE_LEN_MAX
    push line_buffer ; адрес буфера
    call .read_line ; ret rax=количество байт
    add rsp, 16 ; "удалить" значения размера буфера, адреса буфера

    push rax ; длина значения
    push line_buffer ; адрес буфера
    call .ascii_to_int ; ret rax=бинарное представление числа
    add rsp, 16 ; чистка стека

    push rax ; число для конвертации
    push line_buffer ; адрес буфера
    call .string_of ; ret rdx=длина, rax=адрес
    add rsp, 16 ; удалить со стека rax, line_buffer

    push rdx ; количество байт
    push rax ; адрес буфера
    call .write_line ; строка в stdout
    add rsp, 16 ; чистка стека

.exit:
    xor edi, edi
    mov eax, SYS_EXIT
    syscall

.write_line: ; буфер -> stdout
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

.read_line: ; stdin -> буфер
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

.string_of: ; int -> str
    push rbp
    mov rbp, rsp

    mov r10, [rbp+16] ; адрес буфера
    lea rsi, [r10] ; указатель на последний байт буфера
    mov rax, [rbp+24] ; n

    mov r8, 0 ; счетчик цифр
    mov r11, 10 ; делитель

    cmp rax, 0
    jnz .string_of_convert_loop
    mov [rsi], 0x30
    mov r8, 1
    jmp .return_string

    .string_of_convert_loop:
        xor rdx, rdx
        div r11 ; rax - частное, rdx - остаток
        add rdx, 0x30 ; остаток-число -> остаток-asciiкод
        dec rsi ; указатель цифры -=1
        mov [rsi], dl ; запись ascii кода цифры
        inc r8 ; счетчик цифр +=1

        cmp rax, 0 ; частное==0
        jnz .string_of_convert_loop

    .return_string:
    mov rax, rsi ; адрес
    mov rdx, r8 ; длина
    mov rsp, rbp
    pop rbp
    ret

.ascii_to_int:
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
        imul eax, 10 ; следующий разряд
        add eax, r10d
        inc r8
        dec r9
        jmp .build_num

    .build_num_done:
    mov rsp, rbp
    pop rbp
    ret
