;2d_array
;
;## Результат
 ;- Массив 3×4 в `.data` (12 байт, значения 1-12)
 ;- Функция `get_element(row, col)` возвращает значение по координатам
 ;- Функция `sum_row(row)` возвращает сумму элементов строки
 ;- Итоговая сумма всех элементов → RAX → exit code
 ;
 ;## Ожидаемый результат
 ;./step2-5_2d_array; echo $?
 ;# 78    ; 1+2+3+4 + 5+6+7+8 + 9+10+11+12 = 78
; 3 -> [0][2] = 2; 8 -> [1][3] = i*COLS + j; a[2][0]=[2*4+0]=[8] -> 9
default rel

ITEMS_COUNT equ 12
ROWS equ 3
COLS equ 4

section .bss
    matrix_2d resb ITEMS_COUNT ; массив 3*4
section .text
global _start

_start:
    ; инициализация массива
    lea rbx, matrix_2d
    xor rcx, rcx; значение счетчика
    .matrix_init_loop:
        mov al, cl; значение элемента = (счетчик+1)
        inc al
        mov [rbx + rcx], al
        inc cl
        cmp cl, ITEMS_COUNT
        jl .matrix_init_loop

    ; поэлементное суммирование
    xor rdx, rdx ; matrix_2d[i][j] - значение или адрес?
    xor rax, rax ; сумма=0 - значение или адрес?
    
    xor rsi, rsi ; i=строка
    .loop_row:

        xor rcx, rcx ; j=столбец
        .loop_col:
            call .get_element

            inc rcx
            cmp rcx, COLS
            jl .loop_col

        inc rsi
        cmp rsi, ROWS
        jl .loop_row
    jmp _end

.get_element:
    ; возвращает matrix_2d[i][j]
    ; смещение
    mov r10, rsi ; i
    imul r10, COLS ; i*COLS
    add r10, rcx ; i*COLS + j
    add al, [rbx + r10]
    ret

_end:
    mov dil, al
    mov al, 231
    syscall