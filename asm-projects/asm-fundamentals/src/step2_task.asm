;массив + цикл

;Объявить массив в .data: 5 чисел (10, 20, 30, 40, 50)
;Объявить переменную в .bss для результата
;Вычислить сумму всех элементов массива
;Сохранить результат в .bss
;Передать результат как код возврата (exit_group)
;Ожидаемый результат: ./step2_task; echo $? → 150

section .data
    number dd 10, 20, 30, 40, 50

section .bss
    total resq 1

section .text
    global _start

_start:
    lea rsi, [rel number]
    xor rax, rax
    mov rcx, 5 ; 5 итераций

.while_loop:
    mov rdx, [rsi]
    add rax, rdx
    add rsi, 4 ; следующий индекс
    dec rcx
    jnz .while_loop

    mov [rel total], rax
    mov rdi, rax

    mov eax, 231
    syscall