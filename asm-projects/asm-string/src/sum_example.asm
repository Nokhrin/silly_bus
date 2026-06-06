; воспроизведение gcc -O0 -fno-omit-frame-pointer

;nasm -f elf64 src/sum_example.asm -o sum_example.o
;ld sum_example.o -o sum_example
;./sum_example; echo $? -> 15

default rel
SYS_EXIT equ 231
SYS_READ equ 0
SYS_WRITE equ 1

section .bss
section .data
section .text
global _start

_start:
    ; даны 3 переменных типа int
    ; int    -> 4 байта -> NASM: dd  -> Регистр: EAX/EBX
    push rbp ; резервировать 16B для стека, обеспечить rsp%16==0
    mov rbp, rsp
    sub rsp, 4*4 ; [rbp-1], ||>> [rbp-0], [rbp-1], ..., [rbp-11] <<||, [rbp-12],
    mov dword [rbp-1*4], 5 ; a = 5
    mov dword [rbp-2*4], 10 ; b = 10
    mov r8d, [rbp-1*4] ; запись память->память не поддерживается
    add r8d, [rbp-2*4] ; a+b
    mov dword [rbp-3*4], r8d ; c = a+b
    mov edi, [rbp-3*4]
    mov rsp, rbp
    pop rbp
.exit:
    mov eax, SYS_EXIT
    syscall

;sum_example (asm, данный исходник)
;Дизассемблирование раздела .text:
 ;0000000000401000 <_start>:
   ;  401000:	55                   	push   rbp
   ;  401001:	48 89 e5             	mov    rbp,rsp
   ;  401004:	48 83 ec 10          	sub    rsp,0x10
   ;  401008:	c7 45 fc 05 00 00 00 	mov    DWORD PTR [rbp-0x4],0x5
   ;  40100f:	c7 45 f8 0a 00 00 00 	mov    DWORD PTR [rbp-0x8],0xa
   ;  401016:	44 8b 45 fc          	mov    r8d,DWORD PTR [rbp-0x4]
   ;  40101a:	44 03 45 f8          	add    r8d,DWORD PTR [rbp-0x8]
   ;  40101e:	44 89 45 f4          	mov    DWORD PTR [rbp-0xc],r8d
   ;  401022:	8b 7d f4             	mov    edi,DWORD PTR [rbp-0xc]
   ;  401025:	48 89 ec             	mov    rsp,rbp
   ;  401028:	5d                   	pop    rbp

;sum_o2 (дизассемблированный бинарник C, исходник sum_example.c)
;0000000000401116 <main>:
 ;  401116:	55                   	push   rbp
 ;  401117:	48 89 e5             	mov    rbp,rsp
 ;  40111a:	c7 45 fc 05 00 00 00 	mov    DWORD PTR [rbp-0x4],0x5
 ;  401121:	c7 45 f8 0a 00 00 00 	mov    DWORD PTR [rbp-0x8],0xa
 ;  401128:	8b 55 fc             	mov    edx,DWORD PTR [rbp-0x4]
 ;  40112b:	8b 45 f8             	mov    eax,DWORD PTR [rbp-0x8]
 ;  40112e:	01 d0                	add    eax,edx
 ;  401130:	89 45 f4             	mov    DWORD PTR [rbp-0xc],eax
 ;  401133:	8b 45 f4             	mov    eax,DWORD PTR [rbp-0xc]
 ;  401136:	5d                   	pop    rbp
 ;  401137:	c3                   	ret