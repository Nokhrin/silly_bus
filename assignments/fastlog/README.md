# Fastlog - Прикладная утилита на x86-64 NASM

## Цель
разработка однозадачной CLI-утилитой обработки текстовых/бинарных логов с использованием стандартных дескрипторов 0, 1, 2 и glibc только для `exit`

## Задача
Понимание операций над данными в последовательности событий:
высокоуровневые конструкции (Java/Python)
-> ABI/системные вызовы
-> машинные инструкции
-> аппаратное выполнение

понимание архитектуры процессора, памяти, syscall, оптимизация парсинга/сортировки, понимание кэш-локальности и аллокации памяти без GC
Понимание стоимости операций, способов оценки и оптимизации выполнения программы


## Окружение
openSUSE Tumbleweed
x86-64 Linux ABI
System V AMD64 ABI

инструменты: nasm binutils gcc readelf objdump gdb strace

### Настройка окружения в openSUSE:
```shell
sudo zypper install nasm binutils gcc gdb strace
# устранение предупреждения debuginfod
echo 'set debuginfod enabled off' >> ~/.gdbinit
```
```shell
# устранение предупреждения debuginfod
echo 'set debuginfod enabled off' >> ~/.gdbinit
```

```shell
nasm -f elf64 main_example.asm -o main.o && ld main.o -o fastlog
```


## Быстрая проверка окружения
```shell
make clean && make test
# Ожидание: два PASS, отсутствие предупреждений debuginfod
```

## Справка

### Инструкции процессора
[Intel SDM Vol. 2A & 2B (Instruction Set Reference)](https://read.seas.harvard.edu/~kohler/class/aosref/IA32-2A.pdf)
Appendix A

### Системные вызовы
```shell
man 2 syscalls
```

### Руководство разработчика NASM
```shell
export NASM_VERSION=3.02rc6
wget "https://www.nasm.us/pub/nasm/releasebuilds/${NASM_VERSION}/nasm-${NASM_VERSION}-xdoc.tar.xz" -O /tmp/nasm-manual.tar.xz
tar -xf /tmp/nasm-manual.tar.xz -C ~/Documents/ --strip-components=2 nasm-3.02rc6/doc/nasmdoc.txt
```

[Пример создания программы](examples/README.md)

