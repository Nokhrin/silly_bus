; Управление потоком
;Требования:
;
;    Массив в .data: array dd 10, 50, 30, 50, 20 (максимум = 50, вхождения = 2)
;    Переменные в .bss: max_value resd 1, max_count resd 1
;    Два цикла: (1) поиск максимума, (2) подсчёт вхождений
;    Использовать cmp, jg, je, jnz
;    Результат: код возврата = количество вхождений максимума (2)
default rel

section .data
    array dd 10, 50, 30, 50, 20 ; 4 байта на элемент

section .bss
    max_value resd 1
    max_count resd 1

section .text
global _start

_start:
    lea esi, [array] ; esi (4 байта) = &array[0]
    mov eax, [esi] ; верхние 32 бита rax обнуляются при записи в eax
    add esi, 4
    mov ecx, 4 ; счетчик на 4 элемента, так как нулевой уже загружен

.while_find_max:
    mov ebx, [esi]
    cmp ebx, eax ; текущее значение больше текущего max ?
    jg .upd_max ; true => обновить max
    add esi, 4
    loop .while_find_max ; ecx-=1

    mov [max_value], eax
    jmp .count_occurrences

.upd_max:
    mov eax, ebx
    add esi, 4
    loop .while_find_max ; ecx-=1

.count_occurrences:
    xor eax, eax
    xor ebx, ebx
    lea esi, [array] ; esi (4 байта) = &array[0]
    mov ecx, 5 ; счетчик на 5 элементов

.while_count_occurrences:
    mov eax, [esi]
    cmp eax, [max_value]
    je .inc_count_occurrences
    add esi, 4
    loop .while_count_occurrences

    jmp _result

.inc_count_occurrences:
    inc ebx
    add esi, 4
    loop .while_count_occurrences

    mov [max_count], ebx

_result:
    mov edi, ebx

_end:
    mov eax, 231
    syscall


;Вопросы для самопроверки
;
; В чём разница между jg (знаковое) и ja (беззнаковое)?
;   jg - знаковое+больше, ja - беззнаковое+без переноса
; Почему cmp не записывает результат, только флаги?
;    cmp выполняет вычитание, но не записывает его результат, только обновляет флаги - применяется для сравнения, когда вычитание не цель
; Как сохранить значение флага между инструкциями?
;       избежать операции перезаписи флага прыжком , либо поместить значение флага на стек
; Когда использовать dec/jnz вместо loop?
;   когда для основания ветвления требуется применить регистр не rcx, для очевидности изменения значения