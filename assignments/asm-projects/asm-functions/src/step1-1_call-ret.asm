;Псевдокод:
 ;    функция main():
 ;        выровнять стек
 ;        вызвать функцию add_one
 ;        восстановить стек
 ;        завершить программу
 ;
 ;    функция add_one():
 ;        установить RDI = 1
 ;        вернуть
 ;
 ;Входные данные:
 ;    Отсутствуют
 ;
 ;Ожидаемый результат:
 ;    ./step1-1_test; echo $? → 1
 ;
 ;Точки останова:
; === ТОЧКА ОСТАНОВА - Перед call: RSP = 0x7FFFXX00 (выровнено по 16)
; === ТОЧКА ОСТАНОВА - После call (в функции): [RSP] = адрес возврата, RSP = RSP_до - 8
; === ТОЧКА ОСТАНОВА - Перед ret: RSP = 0x7FFFXX00
; === ТОЧКА ОСТАНОВА - После ret (в main): RIP = адрес после call, RSP = 0x7FFFXX08
 
; nasm -f elf64 -g -Werror -Wlabel src/step1-1_call-ret.asm -o step1-1_test.o
; ld -no-pie -m elf_x86_64 step1-1_test.o -o step1-1_test
; gdb -q ./step1-1_test

default rel
global _start

_start:

.main:
;13: /x $ss = 0x2b
;14: /x $esp = 0xffffda10
;15: /x $ebp = 0x0
;16: /x $rip = 0x401000
;17: /x $rcx = 0x0
;18: /x $rax = 0x0
;19: /x $rbx = 0x0
;20: /x $rdx = 0x0
;21: /x $rsi = 0x0
;22: /x $rdi = 0x0
    sub rsp, 8
; === ТОЧКА ОСТАНОВА 1 - Перед call: RSP = 0x7FFFXX00 (выровнено по 16)
;2: /x $ss = 0x2b
;3: /x $esp = 0xffffda08
;4: /x $ebp = 0x0
;5: /x $rip = 0x401004
;6: /x $rcx = 0x0
;7: /x $rax = 0x0
;8: /x $rbx = 0x0
;9: /x $rdx = 0x0
;10: /x $rsi = 0x0
;11: /x $rdi = 0x0
    call .add_one

; === ТОЧКА ОСТАНОВА - После ret (в main): RIP = адрес после call, RSP = 0x7FFFXX08
;2: /x $ss = 0x2b
;3: /x $esp = 0xffffda08
;4: /x $ebp = 0x0
;5: /x $rip = 0x401009
;6: /x $rcx = 0x0
;7: /x $rax = 0x0
;8: /x $rbx = 0x0
;9: /x $rdx = 0x0
;10: /x $rsi = 0x0
;11: /x $rdi = 0x1
    add rsp, 8
; === ТОЧКА ОСТАНОВА - После ret (в main): RIP = адрес после call, RSP = 0x7FFFXX08
;2: /x $ss = 0x2b
;3: /x $esp = 0xffffda10
;4: /x $ebp = 0x0
;5: /x $rip = 0x40100d
;6: /x $rcx = 0x0
;7: /x $rax = 0x0
;8: /x $rbx = 0x0
;9: /x $rdx = 0x0
;10: /x $rsi = 0x0
;11: /x $rdi = 0x1
    jmp .end

.add_one:
; === ТОЧКА ОСТАНОВА 2 - После call (в функции): [RSP] = адрес возврата, RSP = RSP_до - 8
;2: /x $ss = 0x2b
;3: /x $esp = 0xffffda00
;4: /x $ebp = 0x0
;5: /x $rip = 0x40100f
;6: /x $rcx = 0x0
;7: /x $rax = 0x0
;8: /x $rbx = 0x0
;9: /x $rdx = 0x0
;10: /x $rsi = 0x0
;11: /x $rdi = 0x0
    mov rdi, 1

; === ТОЧКА ОСТАНОВА 3 - Перед ret: RSP = 0x7FFFXX00
;2: /x $ss = 0x2b
;3: /x $esp = 0xffffda00
;4: /x $ebp = 0x0
;5: /x $rip = 0x40100f
;6: /x $rcx = 0x0
;7: /x $rax = 0x0
;8: /x $rbx = 0x0
;9: /x $rdx = 0x0
;10: /x $rsi = 0x0
;11: /x $rdi = 0x0
    ret

.end:
    mov al, 60 ; sys_exit
    syscall

; ./step1-1_test; echo $?
; 1