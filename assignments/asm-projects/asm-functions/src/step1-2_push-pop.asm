;Кадр стека
;Псевдокод:
 ;    функция main():
 ;        выровнять стек
 ;        вызвать функцию get_value
 ;        восстановить стек
 ;        завершить программу с кодом из RDI
 ;    
 ;    функция get_value():
 ;        создать кадр стека (push rbp / mov rbp, rsp)
 ;        записать значение 42 в [rbp-8]
 ;        прочитать значение из [rbp-8] в RDI
 ;        удалить кадр стека (pop rbp / ret)
 ;
 ;Входные данные:
 ;    Отсутствуют
 ;
 ;Ожидаемый результат:
 ;    ./step1-2_test; echo $?
 ; 42

;nasm -f elf64 -g -Werror -Wlabel src/step1-2_push-pop.asm -o step1-2_test.o
;ld -no-pie -m elf_x86_64 step1-2_test.o -o step1-2_test
;gdb -q ./step1-2_test

default rel
global _start

;Кадр стека
;Псевдокод:
 ;    функция main():
 ;        выровнять стек
 ;        вызвать функцию get_value
 ;        восстановить стек
 ;        завершить программу с кодом из RDI
 ;
 ;    функция get_value():
 ;        создать кадр стека (push rbp / mov rbp, rsp)
 ;        записать значение 42 в [rbp-8]
 ;        прочитать значение из [rbp-8] в RDI
 ;        удалить кадр стека (pop rbp / ret)
 ;
 ;Входные данные:
 ;    Отсутствуют
 ;
 ;Ожидаемый результат:
 ;    ./step1-2_test; echo $?
 ; 42

;nasm -f elf64 -g -Werror -Wlabel src/step1-2_push-pop.asm -o step1-2_test.o
;ld -no-pie -m elf_x86_64 step1-2_test.o -o step1-2_test
;gdb -q ./step1-2_test

default rel
global _start

_start:
.main:
    sub rsp, 8
    call .get_value
    add rsp, 8

    jmp .end

.get_value:
;2: /x $ss = 0x2b
;3: /x $rsp = 0x7fffffffda00
;4: /x $rbp = 0x0
;5: /x $rip = 0x40100d

    push rbp
    ; === ТОЧКА ОСТАНОВА - 1. После push rbp: [RSP] = старый RBP, RSP = RSP_до - 8
;2: /x $ss = 0x2b
;3: /x $rsp = 0x7fffffffd9f8 - дельта=8
;4: /x $rbp = 0x0
;5: /x $rip = 0x40100e
    mov rbp, rsp ; адрес вершины стека
    ; === ТОЧКА ОСТАНОВА - 2. После mov rbp, rsp: RBP = RSP
;2: /x $ss = 0x2b
;3: /x $rsp = 0x7fffffffd9f8
;4: /x $rbp = 0x0
;5: /x $rip = 0x40100e
    mov [rbp-8], 42
    ; === ТОЧКА ОСТАНОВА - 3. После записи [rbp-8]: [RBP-8] = 42 (0x2A)
;(gdb) x/dw $rbp-8
;0x7fffffffd9f0:	42
    mov rdi, [rbp-8]
    pop rbp
    ; === ТОЧКА ОСТАНОВА - 4. После pop rbp: RBP = старый RBP, RSP = RSP_до + 8
;2: /x $ss = 0x2b
;3: /x $rsp = 0x7fffffffda00
;4: /x $rbp = 0x0
;5: /x $rip = 0x40101a
;11: /x $rdi = 0x2a
    ret

.end:
    ; результат помещен в rdi ранее
    mov al, 60 ; sys_exit
    syscall
