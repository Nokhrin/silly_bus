;Базовый вызов функции
;
;
;Входные данные:
;a = 15
;b = 4
;
;Ожидаемый результат:
;add(15, 4) = 19
;multiply(15, 4) = 60
;subtract(15, 4) = 11
;итог = 19 + 60 + 11 = 90
;./step1-6_final; echo $? → 90
;
default rel

%macro PROLOGUE 0 ; создание фрейма стека
    push rbp ; rsp-=8, [rsp]=rbp -> память[rsp-8] = rbp -> база кадра сохранена в rsp-8
    mov rbp, rsp ; rbp=rsp -> новая база кадра
%endmacro

%macro EPILOGUE 0 ; удаление фрейма стека
    mov rsp, rbp ; rsp=rbp -> восстановление базы
    pop rbp ; rsp+=8, [rsp]=rbp -> память[rsp+8]
%endmacro

ALIGN_STACK equ 8 ; выравнивание стека перед call
SYS_EXIT equ 60 ; код системного вызова exit

section .data
    input_a dd 15
    input_b dd 4

section .text
global _start

_start:
    ; результат сохраняется в накопителе - eax
; === ТОЧКА ОСТАНОВА === RSP % 16 = 0
;=> 0x401000 <_start>:	call   0x401011 <_start.add>
 ;2: /x $ss = 0x2b
 ;3: /x $rsp = 0x7fffffffd940
 ;4: /x $rbp = 0x0
 ;5: /x $rip = 0x401000
 ;6: /x $rcx = 0x0
 ;7: /x $rax = 0x0
 ;8: /x $rbx = 0x0
 ;9: /x $rdx = 0x0
 ;10: /x $rsi = 0x0
 ;11: /x $rdi = 0x0
 ;(gdb) p (long)$rsp % 16
 ;$1 = 0

    call .add
; === ТОЧКА ОСТАНОВА === 2. eax = input_a + input_b
;=> 0x401005 <_start+5>:	call   0x401029 <_start.multiply>
 ;2: /x $ss = 0x2b
 ;3: /x $rsp = 0x7fffffffd940   <<<
 ;4: /x $rbp = 0x0              <<<
 ;5: /x $rip = 0x401005
 ;6: /x $rcx = 0x0
 ;7: /x $rax = 0x13

    call .multiply
    call .subtract
    jmp .total

.add:
    PROLOGUE
    mov eax, [input_a]
    add eax, [input_b]
;=> 0x401021 <_start.add+16>:	mov    r12d,eax
 ;2: /x $ss = 0x2b
 ;3: /x $rsp = 0x7fffffffd930   <<<
 ;4: /x $rbp = 0x7fffffffd930   <<<
 ;5: /x $rip = 0x401021
 ;6: /x $rcx = 0x0
 ;7: /x $rax = 0x13s
    mov r12d, eax ; временный накопитель
    EPILOGUE
    ret

.multiply:
    PROLOGUE
    mov eax, [input_a]
    imul eax, [input_b]
    mov r13d, eax ; временный накопитель
    EPILOGUE
    ret

.subtract:
    PROLOGUE
    mov eax, [input_a]
    sub eax, [input_b]
    mov r14d, eax ; временный накопитель
    EPILOGUE
    ret

.total:
    mov eax, r12d
    add eax, r13d
    add eax, r14d

.end:
    mov edi, eax
    mov al, SYS_EXIT
; === ТОЧКА ОСТАНОВА === EDI = 90, RSP % 16 = 0
;=> 0x401067 <_start.end+4>:	syscall
 ;2: /x $ss = 0x2b
 ;3: /x $rsp = 0x7fffffffd940
 ;4: /x $rbp = 0x0
 ;5: /x $rip = 0x401067
 ;6: /x $rcx = 0x0
 ;7: /x $rax = 0x3c
 ;8: /x $rbx = 0x0
 ;9: /x $rdx = 0x0
 ;10: /x $rsi = 0x0
 ;11: /x $rdi = 0x5a
    syscall