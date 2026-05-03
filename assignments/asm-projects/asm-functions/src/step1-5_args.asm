;аргументы
;
;Входные данные:
;    a = 10 (в RDI)
;    b = 20 (в RSI)
;
;Ожидаемый результат:
;    ./step1-5_test; echo $? → 30

global _start

ALIGN_STACK equ 8 ; выравнивание стека перед call

%define INPUT_A 10
%define INPUT_B 20

%macro PROLOGUE 0 ; создание фрейма стека
    push rbp ; rsp-=8, [rsp]=rbp -> память[rsp-8] = rbp -> база кадра сохранена в rsp-8
    mov rbp, rsp ; rbp=rsp -> новая база кадра
%endmacro

%macro EPILOGUE 0 ; удаление фрейма стека
    mov rsp, rbp ; rsp=rbp -> восстановление базы
    pop rbp ; rbp+=8, [rsp]=rbp -> память[rbp+8]
%endmacro

section .text

_start:
    mov rdi, INPUT_A
    mov rsi, INPUT_B

    sub rsp, ALIGN_STACK ; выровнять стек

; === ТОЧКА ОСТАНОВА - 1. Перед call: RDI = 10, RSI = 20
;=> 0x40100e <_start+14>:	call   0x40101a <_start.add>
 ;2: /x $ss = 0x2b
 ;3: /x $rsp = 0x7fffffffd958
 ;4: /x $rbp = 0x0
 ;5: /x $rip = 0x40100e
 ;6: /x $rcx = 0x0
 ;7: /x $rax = 0x0
 ;8: /x $rbx = 0x0
 ;9: /x $rdx = 0x0
 ;10: /x $rsi = 0x14            <<<
 ;11: /x $rdi = 0xa             <<<
    call .add ; rsp-=8
    add rsp, ALIGN_STACK ; выровнять стек

; === ТОЧКА ОСТАНОВА - 4. После ret: RAX = 30
;=> 0x401013 <_start.end>:	mov    rdi,rax
 ;2: /x $ss = 0x2b
 ;3: /x $rsp = 0x7fffffffd958
 ;4: /x $rbp = 0x0
 ;5: /x $rip = 0x401013
 ;6: /x $rcx = 0x0
 ;7: /x $rax = 0x1e             <<<
 ;8: /x $rbx = 0x0
 ;9: /x $rdx = 0x0
 ;10: /x $rsi = 0x14
 ;11: /x $rdi = 0xa
.end:
    mov rdi, rax ; вывод результата как кода выхода
    mov al, 60
    syscall

.add:
; === ТОЧКА ОСТАНОВА - 2. В функции add: RDI = 10, RSI = 20
;=> 0x40101a <_start.add>:	push   rbp
 ;2: /x $ss = 0x2b
 ;3: /x $rsp = 0x7fffffffd950
 ;4: /x $rbp = 0x0
 ;5: /x $rip = 0x40101a
 ;6: /x $rcx = 0x0
 ;7: /x $rax = 0x0
 ;8: /x $rbx = 0x0
 ;9: /x $rdx = 0x0
 ;10: /x $rsi = 0x14                <<<
 ;11: /x $rdi = 0xa                 <<<
    PROLOGUE
    mov rax, rdi
    add rax, rsi
    EPILOGUE
; === ТОЧКА ОСТАНОВА - 3. Перед ret: RAX = 30
;=> 0x401028 <_start.add+14>:	ret
 ;2: /x $ss = 0x2b
 ;3: /x $rsp = 0x7fffffffd950
 ;4: /x $rbp = 0x0
 ;5: /x $rip = 0x401028
 ;6: /x $rcx = 0x0
 ;7: /x $rax = 0x1e             <<<
 ;8: /x $rbx = 0x0
 ;9: /x $rdx = 0x0
 ;10: /x $rsi = 0x14
 ;11: /x $rdi = 0xa
    ret ; rsp+=8

