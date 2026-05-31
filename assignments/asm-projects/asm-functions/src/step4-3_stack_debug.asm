;stack_debug

default rel
extern write_line

section .data
msg_chain db "=== Frame Chain ===", 10
msg_chain_len equ $ - msg_chain

section .text
global _start

_start:
push msg_chain_len
push msg_chain
call write_line
add rsp, 16

push 4
call .walk_stack
add rsp, 8

.exit:
xor edi, edi
mov eax, 231
syscall

.walk_stack:
push rbp
mov rbp, rsp
sub rsp, 16

mov rax, [rbp+16]
cmp rax, 0
jle .base_case

mov qword [rbp], rax ; дефект - уничтожен адрес базы кадра стека

dec rax
push rax
call .walk_stack
add rsp, 8

.base_case:
add rsp, 16
mov rsp, rbp
pop rbp ; дефект (rip = 0x1) проявляется при использовании некорректного значения в качестве адреса
;rip = 0x40112e in _start.base_case (src/step_test.asm:47); saved rip = 0x1
;(gdb) x $rsp
;0x7fffffffd910:	0x00000001
ret
