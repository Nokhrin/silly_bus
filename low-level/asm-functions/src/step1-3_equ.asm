;Символы и метки
;Ожидаемый результат:
 ;    ./step1-3_test; echo $? → 42
 ;
 ;Проверка символов:
 ;    readelf -s step1-3_test | grep -E "EXIT_CODE|STACK_ALIGN"
 ;    # equ не попадает в таблицу символов (препроцессор)

default rel
global _main

EXIT_CODE equ 42
STACK_ALIGN equ 8

_main:

; === ТОЧКА ОСТАНОВА - 1. Перед sub rsp: RSP = 0x7FFFXX00
;Breakpoint 1, _main () at src/step1-4_macro.asm:18
;18	    sub rsp, STACK_ALIGN
;1: x/i $pc
;=> 0x401000 <_main>:	sub    rsp,0x8
;2: /x $ss = 0x2b
;3: /x $rsp = 0x7fffffffd980
;4: /x $rbp = 0x0
;5: /x $rip = 0x401000
    sub rsp, STACK_ALIGN

; === ТОЧКА ОСТАНОВА - 2. После sub rsp: RSP = 0x7FFFXXF8
;(gdb) si
;_main () at src/step1-4_macro.asm:24
;24	    mov dil, EXIT_CODE
;1: x/i $pc
;=> 0x401004 <_main+4>:	mov    dil,0x2a
;2: /x $ss = 0x2b
;3: /x $rsp = 0x7fffffffd978
;4: /x $rbp = 0x0
;5: /x $rip = 0x401004
;6: /x $rcx = 0x0
;7: /x $rax = 0x0
;8: /x $rbx = 0x0
;9: /x $rdx = 0x0
;10: /x $rsi = 0x0
;11: /x $rdi = 0x0

; === ТОЧКА ОСТАНОВА - 3. Перед exit: RDI = 42 (0x2A)
;(gdb) si
;25	    mov al, 231
;1: x/i $pc
;=> 0x401007 <_main+7>:	mov    al,0xe7
;2: /x $ss = 0x2b
;3: /x $rsp = 0x7fffffffd978
;4: /x $rbp = 0x0
;5: /x $rip = 0x401007
;6: /x $rcx = 0x0
;7: /x $rax = 0x0
;8: /x $rbx = 0x0
;9: /x $rdx = 0x0
;10: /x $rsi = 0x0
;11: /x $rdi = 0x2a
.exit:
    mov dil, EXIT_CODE
    mov al, 231
    syscall
