# Environment Smoke Test (x86-64 Linux Toolchain Verification)


## Пример создания программы

### Записать исходный код в main_example.asm

### Выполнить компоновку
```shell
nasm -f elf64 main_example.asm -o main_example.o
```

### Выполнить линковку
```shell
ld -no-pie -m elf_x86_64 main_example.o -o main_example
```

### Выполнить бинарь
```shell
./main_example
```

### Способы корректного завершения программы
Условие: rax=0, т.е. EOF как следствие read -> 0
1. Конец файла: `./fastlog < file.txt`
2. Пустой поток: `./fastlog < /dev/null`
3. Закрытие stdin в пайпе: `echo "x" \| ./fastlog`
4. Ручной EOF в терминале: `Ctrl+D` (Linux)


## Отладка
отключение переноса строк в выводе:
readelf -W ... 

синтаксис доступа к регистрам в GDB:
```shell
(gdb) info registers rax  # инфо о регистре
(gdb) print $rax          # десятичный: 0
(gdb) print/x $rax        # шестнадцатеричный: 0x0
(gdb) print/t $rax        # бинарный: 00000000...
(gdb) print/d $rax        # знаковый десятичный
```

### Проверить линковки
```shell
file main_example
# main_example: ELF 64-bit LSB executable, x86-64, version 1 (SYSV), statically linked, not stripped
```

### Проверить exit code
```shell
./main_example; echo "exit code: $?"
# exit code: 0
```

### Выполнить трассировку
```shell
strace -e exit_group -q ./main_example
```

### Выполнить отладку
```shell
gdb -q -batch -ex "break _start" -ex "run" -ex "info registers rip rax rdi" -ex "quit" ./main_example
```

### Статические данные: .text
```shell
objdump -s main_example
```

### Резервирование: .bss
```shell
readelf -W -S main_example
```

### Код: Пошаговое выполнение _start
```shell
gdb break _start main_example
```

### Системные вызовы: read/write
```shell
strace -e read,write main_example
```

## Пример отладки - main_example.asm

```shell
echo -e "line1\nline2\n" > test.log
nasm -f elf64 -g main_example.asm -o main.o
ld -no-pie -m elf_x86_64 main.o -o fastlog_test
```

```shell
gdb ./fastlog_test
(gdb) break _start
(gdb) run < test.log
# Starting program: .../examples/fastlog_test < test.log
```

```
Breakpoint 1, _start () at main_example.asm:11
11	    mov rax, 0 ; sys_read
(gdb) display/i $pc
1: x/i $pc
=> 0x401000 <_start>:	mov    $0x0,%eax
(gdb) stepi
12	    mov rdi, 0 ; stdin / fd 0
1: x/i $pc
=> 0x401005 <_start+5>:	mov    $0x0,%edi
(gdb) stepi
13	    mov rsi, buf
1: x/i $pc
=> 0x40100a <_start+10>:	movabs $0x402000,%rsi
(gdb) stepi
14	    mov rdx, 4096
1: x/i $pc
=> 0x401014 <_start+20>:	mov    $0x1000,%edx
(gdb) stepi
15	    syscall
1: x/i $pc
=> 0x401019 <_start+25>:	syscall
(gdb) Quit
(gdb) stepi
18	    cmp rax, 0
1: x/i $pc
=> 0x40101b <_start+27>:	cmp    $0x0,%rax
(gdb) stepi
19	    jle .exit
1: x/i $pc
=> 0x40101f <_start+31>:	jle    0x40103f <_start.exit>
(gdb) info registers
rax            0xd                 13
rbx            0x0                 0
rcx            0x40101b            4198427
rdx            0x1000              4096
rsi            0x402000            4202496
rdi            0x0                 0
rbp            0x0                 0x0
rsp            0x7fffffffd9d0      0x7fffffffd9d0
r8             0x0                 0
r9             0x0                 0
r10            0x0                 0
r11            0x302               770
r12            0x0                 0
r13            0x0                 0
r14            0x0                 0
r15            0x0                 0
rip            0x40101f            0x40101f <_start+31>
eflags         0x202               [ IF ]
cs             0x33                51
ss             0x2b                43
ds             0x0                 0
es             0x0                 0
fs             0x0                 0
gs             0x0                 0
fs_base        0x0                 0
gs_base        0x0                 0
(gdb) stepi
21	    mov r10, rax
1: x/i $pc
=> 0x401021 <_start+33>:	mov    %rax,%r10
(gdb) info registers r10
r10            0x0                 0
(gdb) stepi
24	    mov rax, 1 ; sys_write
1: x/i $pc
=> 0x401024 <_start+36>:	mov    $0x1,%eax
(gdb) info registers r10
r10            0xd                 13
(gdb) stepi
25	    mov rdi, 1 ; stdout / fd 1
1: x/i $pc
=> 0x401029 <_start+41>:	mov    $0x1,%edi
(gdb) stepi
26	    mov rsi, buf
1: x/i $pc
=> 0x40102e <_start+46>:	movabs $0x402000,%rsi
(gdb) stepi
27	    mov rdx, r10
1: x/i $pc
=> 0x401038 <_start+56>:	mov    %r10,%rdx
(gdb) stepi
28	    syscall
1: x/i $pc
=> 0x40103b <_start+59>:	syscall
(gdb) stepi
line1
line2

31	    jmp _start
1: x/i $pc
=> 0x40103d <_start+61>:	jmp    0x401000 <_start>
(gdb) info registers rax
rax            0xd                 13
(gdb) stepi

Breakpoint 1, _start () at main_example.asm:11
11	    mov rax, 0 ; sys_read
1: x/i $pc
=> 0x401000 <_start>:	mov    $0x0,%eax
(gdb) stepi
12	    mov rdi, 0 ; stdin / fd 0
1: x/i $pc
=> 0x401005 <_start+5>:	mov    $0x0,%edi
```