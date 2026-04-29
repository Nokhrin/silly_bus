; возвращает сумму и произведение двух целых чисел [0;99]
;
; Требования:
  ;
  ; Парсинг
  ;    Прочитать строковое представление числа num из stdin (до \n)
        ; или 0, или 1, или 2 цифры + \n == [digit] \n | digit digit \n
        ; получить последовательность бай, представляющих ascii коды - пример: '42' -> [52, 50] == [0x34, 0x32]
  ;    Преобразовать num из ascii кода в integer: [0x34, 0x32] -> [0x34-0x30, 0x32-0x30] -> [0x4, 0x2]
  ;
  ; Порядок работы
  ;     1. Выполнить парсинг значений num1, num2
            ; [42, 2] -> [0x2a, 0x02]
  ;     2. Вычислить sum = num1 + num2
;           sum([0x2a, 0x02]) = 0x2c
  ;     3. Вычислить prod = num1 * num2
    ;       prod([0x2a, 0x02]) = 0x54
  ;     4. Преобразовать sum из числа в последовательность ascii кодов
    ;       0x54 -> 84 -> ['8', '4']
  ;     5. Вывести строку: Sum: <сумма>\nProd: <произведение>\n
  ;         - вывести "Sum: "
  ;         - вывести sum
  ;         - вывести "\n"
  ;         - вывести "Prod: "
  ;         - вывести prod
  ;         - вывести "\n"
  ;
  ;Ограничения:
  ;
  ;    Числа от 0 до 99 (1-2 цифры)
  ;    Без использования libc
  ;    Без использования процедур (call, ret) - только условные и безусловные переходы
  ;    Только read/write/exit_group

default rel
global _start

section .data
    ; константы по условию
    msg_sum db 'Sum: '
    msg_sum_len equ $ - msg_sum

    msg_prod db 'Prod: '
    msg_prod_len equ $ - msg_prod

    newline db 10
    newline_len equ $ - newline

section .bss
    input_buf resb 4 ; 2 цифры + \n + \0 -> 4 байта
    num1 resd 1 ; или 1, или 2 символа по 2 байта -> резервировать 4 байта
    num2 resd 1 ; или 1, или 2 символа по 2 байта -> резервировать 4 байта

    sum_result resb 4 ; максимальное значение 198 -> 3 байта -> 4 байта для удобства записи в память
    prod_result resb 4 ; максимальное значение 9801 -> 4 байта

    output_buf resb 8 ; максимальное значение 'Prod: ' -> 6 байт -> 8 байт для удобства записи в память

section .text

_start:

; === БЛОК 1: Чтение num1 из stdin ===
.read_num1:
    lea rsi, [input_buf]
    mov rdx, 4 ; макс 4 байта
    mov rdi, 0 ; stdin
    mov rax, 0 ; sys_read
    syscall ; rip -> rcx !!!
    cmp rax, 0
; === ТОЧКА ОСТАНОВА ===
; b *0x40101c
; r
; <input> <- 42, enter
; x/3cb &input_buf
; 0x402010 <input_buf>:	52 '4'	50 '2'	10 '\n'
; p $rax
; 0x3
; p/t $eflags
; $5 = 100(0)000110 -> ZF=0 -> jle .end не выполняется
    jle .end

; === БЛОК 2: num1: ASCII -> integer ===
.ascii2int_num1:
    lea rsi, [input_buf]
    mov ecx, eax ; количество считанных байт -> счетчик цикла
    xor eax, eax
    xor ebx, ebx
; === ТОЧКА ОСТАНОВА ===
; b *0x4010..
; 2: /x $rax = 0x4
; 3: /x $rbx = 0x4
; 4: /x $rcx = 0x3
; 5: /x $rdx = 0x4
; 6: /x $rsi = 0x40200d
    .digits_loop_num1:
        mov bl, [rsi] ; один нижний байт
        cmp bl, 0xa ; rax=='\n' ?
        jz .end_num1
        sub bl, 0x30

        imul eax, 10
        add eax, ebx
        inc rsi ; следующий байт

; === ТОЧКА ОСТАНОВА - итерация 1 ===
; b *0x401031
; (gdb) p/x $rax
; 0x4
; (gdb) p/t $eflags
; $5 = 1001000110 -> PF, ZF, IF
        loop .digits_loop_num1

.end_num1:
    mov dword [num1], eax
; === ТОЧКА ОСТАНОВА ===
; x/dw &num1
; 0x402010 <num1>:	42

; === БЛОК 3: Чтение num2 из stdin ===
.read_num2:
    lea rsi, [input_buf]
    mov rdx, 4 ; макс 4 байта
    mov rdi, 0 ; stdin
    mov rax, 0 ; sys_read
    syscall ; rip -> rcx !!!
    cmp rax, 0
; === ТОЧКА ОСТАНОВА ===
; Ожидание: RAX = количество прочитанных байт, input_buf = ['2','\n']
; b *0x...
; r
; <input> <- 2, enter
; x/3cb &input_buf
; 0x402010 <input_buf>:	52 '4'	50 '2'	10 '\n'
; p $rax
; 0x3
; p/t $eflags
; $5 = 100(0)000110 -> ZF=0 -> jle .end не выполняется
    jle .end

; === БЛОК 4: num1: ASCII -> integer ===
.ascii2int_num2:
    lea rsi, [input_buf]
    mov ecx, eax ; количество считанных байт -> счетчик цикла
    xor eax, eax
    xor ebx, ebx
; === ТОЧКА ОСТАНОВА ===
; b *0x...
; rax            0x0                 0
; rbx            0x0                 0
; rcx            0x2                 2
; rdx            0x4                 4
; rsi            0x40200c            4202508
    .digits_loop_num2:
        mov bl, [rsi] ; один нижний байт
        cmp bl, 0xa ; rax=='\n' ?
        jz .end_num2
        sub bl, 0x30

        imul eax, 10
        add eax, ebx
        inc rsi ; следующий байт

        loop .digits_loop_num2

.end_num2:
    mov dword [num2], eax
; === ТОЧКА ОСТАНОВА ===
; x/dw &num2
; 0x402014 <num2>:	2

; === БЛОК 5: Вычисления ===
; Ожидание: sum_result = 44, prod_result = 84
.calc:
    mov eax, [num1]
    mov ebx, [num2]
    add eax, ebx
    mov dword [sum_result], eax

    mov eax, [num1]
    mov ebx, [num2]
    imul eax, ebx
    mov dword [prod_result], eax

; === ТОЧКА ОСТАНОВА ===
; (gdb) x/dw &sum_result
; 0x402018 <sum_result>:	44
; (gdb) x/dw &prod_result
; 0x40201c <prod_result>:	84

; === БЛОК 6: Вывод "Sum: " ===
.write_msg_sum:
    lea rsi, [msg_sum]
    mov rdx, msg_sum_len ; количество байт
    mov rdi, 1 ; stdout
    mov rax, 1 ; sys_write
    syscall

; === БЛОК 7: Конверсия и вывод sum_result ===
; Ожидание: stdout = "44\n"
.int2ascii_sum:
    mov eax, [sum_result]
    lea rdi, [output_buf] ; начало буфера
    lea rsi, [output_buf] ; текущая позиция

.write_sum_3: ; сотни
    cmp eax, 100
    jl .write_sum_2
    xor edx, edx
    mov ebx, 100
    div ebx ; eax/100 -> пример 123/100 -> rdx=1, rax=23

    add al, 0x30
    mov [rsi], al
    inc rsi
    mov eax, edx

.write_sum_2: ; десятки
    cmp eax, 10
    jl .write_sum_1
    xor edx, edx
    mov ebx, 10
    div ebx ; eax/10 -> пример 23/10 -> rdx=2, rax=3

    add al, 0x30
    mov [rsi], al
    inc rsi
    mov eax, edx

.write_sum_1: ; единицы
    add al, 0x30
    mov [rsi], al
    inc rsi

    mov rdx, rsi ; rsi=номер последнего байта
    sub rdx, rdi ; rdi=номер первого байта -> rdx=количество байт
    mov rsi, rdi ; указатель на первый байт для вывода

    mov rdi, 1 ; stdout
    mov rax, 1 ; sys_write
    syscall
; === ТОЧКА ОСТАНОВА ===
; (gdb) x/4cb &output_buf
; 0x402020 <output_buf>:	52 '4'	52 '4'	0 '\000'	0 '\000'

; === БЛОК 8: Вывод \n ===
.write_sum_result_newline:
    lea rsi, [newline]
    mov rdx, newline_len ; количество байт
    mov rdi, 1 ; stdout
    mov rax, 1 ; sys_write
    syscall

; === БЛОК 9: Вывод "Prod: " ===
.write_msg_prod:
    lea rsi, [msg_prod]
    mov rdx, msg_prod_len ; количество байт
    mov rdi, 1 ; stdout
    mov rax, 1 ; sys_write
    syscall

; === БЛОК 10: Конверсия и вывод prod_result ===
; Ожидание: stdout = "84\n"
.int2ascii_prod:
    mov eax, [prod_result]
    lea rdi, [output_buf] ; начало буфера
    lea rsi, [output_buf] ; текущая позиция

.write_prod_4: ; тысячи
    cmp eax, 1000
    jl .write_prod_3
    xor edx, edx
    mov ebx, 1000
    div ebx ; eax/1000 -> пример 1234/1000 -> rdx=1, rax=234

    add al, 0x30
    mov [rsi], al
    inc rsi
    mov eax, edx

.write_prod_3: ; сотни
    cmp eax, 100
    jl .write_prod_2
    xor edx, edx
    mov ebx, 100
    div ebx ; eax/100 -> пример 234/100 -> rdx=2, rax=34

    add al, 0x30
    mov [rsi], al
    inc rsi
    mov eax, edx

.write_prod_2: ; десятки
    cmp eax, 10
    jl .write_prod_1
    xor edx, edx
    mov ebx, 10
    div ebx ; eax/10 -> пример 34/10 -> rdx=3, rax=4

    add al, 0x30
    mov [rsi], al
    inc rsi
    mov eax, edx

.write_prod_1: ; единицы
    add al, 0x30
    mov [rsi], al
    inc rsi

    mov rdx, rsi ; rsi=номер последнего байта
    sub rdx, rdi ; rdi=номер первого байта -> rdx=количество байт
    mov rsi, rdi ; указатель на первый байт для вывода

    mov rdi, 1 ; stdout
    mov rax, 1 ; sys_write
    syscall
; === ТОЧКА ОСТАНОВА ===
; (gdb) x/4cb &output_buf
; 0x402020 <output_buf>:	56 '8'	52 '4'	0 '\000'	0 '\000'


; === БЛОК 11: Вывод \n ===
.write_prod_result_newline:
    lea rsi, [newline]
    mov rdx, newline_len ; количество байт
    mov rdi, 1 ; stdout
    mov rax, 1 ; sys_write
    syscall

; === БЛОК 12: Завершение ===
.end:
    xor rdi, rdi
    mov rax, 0xe7 ; exit_group
    syscall
