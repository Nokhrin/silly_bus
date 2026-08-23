;макросы препроцессора
;
;Ожидаемый результат:
;    ./step1-4_test; echo $? → 100
;

global _start

%define LOCAL1_OFFSET -8

%macro PROLOGUE 0
    push rbp    ; rsp-=8 , [rsp]=rbp - вершина стека указывает на значение rbp
    mov rbp, rsp ; rbp=rsp
%endmacro

%macro EPILOGUE 0
    mov rsp, rbp ; rsp=rbp - вершина стека -> база кадра стека
    pop rbp ; rsp+=8 , rbp=[rsp] - записать в регистр rbp значение с вершины стека
%endmacro

_start:
    PROLOGUE
; === ТОЧКА ОСТАНОВА === 1. После PROLOGUE: RBP = RSP
;=> 0x401004 <_start+4>:	mov    QWORD PTR [rbp-0x8],0x64
;2: /x $ss = 0x2b
;3: /x $rsp = 0x7fffffffd978
;4: /x $rbp = 0x7fffffffd978
;5: /x $rip = 0x401004
;6: /x $rcx = 0x0
;7: /x $rax = 0x0
;8: /x $rbx = 0x0
;9: /x $rdx = 0x0
;10: /x $rsi = 0x0
;11: /x $rdi = 0x0

    mov qword [rbp+LOCAL1_OFFSET], 100
; === ТОЧКА ОСТАНОВА - 2. После записи: [RBP-8] = 100
;(gdb) x/dw $rbp-8
;0x7fffffffd970:	100

    mov rdi, [rbp+LOCAL1_OFFSET]
; === ТОЧКА ОСТАНОВА - 3. Перед EPILOGUE: RDI = 100
;=> 0x401010 <_start+16>:	mov    rsp,rbp
;2: /x $ss = 0x2b
;3: /x $rsp = 0x7fffffffd978
;4: /x $rbp = 0x7fffffffd978
;5: /x $rip = 0x401010
;6: /x $rcx = 0x0
;7: /x $rax = 0x0
;8: /x $rbx = 0x0
;9: /x $rdx = 0x0
;10: /x $rsi = 0x0
;11: /x $rdi = 0x64
    EPILOGUE

    mov al, 60
    syscall
