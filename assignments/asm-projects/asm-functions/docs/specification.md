# Учебный проект `asm-functions` - 16 часов

## Цель
Понимание runtime-сред (JVM, CPython), отладка крашей, оптимизация вызовов.
call/ret для модульности кода, стек для локальных переменных, отладка в GDB backtrace

## Задача
Реализовать механизм вызова функций как в Python/Java с помощью стека x86-64.

## Тех. ограничения
- Linux x86-64, NASM синтаксис
- System V AMD64 ABI (аргументы в регистрах)
- Выравнивание стека 16 байт перед call
- Без libc (только syscall write/exit_group)

## Задачи

### Спринт 1: Базовый вызов функции (4 часа)
- Инструкция call/ret (адрес возврата в стек)
- Кадр стека (push rbp / mov rbp, rsp / pop rbp)
- Передача аргументов (RDI, RSI, RDX, RCX, R8, R9)
- Возврат значения (RAX)

Итог: Функция `add(a, b)` → `return a + b`

### Спринт 2: Сохранение регистров (4 часа)
- Caller-saved (RAX, RCX, RDX, RSI, RDI, R8-R11)
- Callee-saved (RBX, RBP, R12-R15)
- push/pop в прологе/эпилоге функции

Итог: Функция сохраняет RBX/R12 между вызовами

### Спринт 3: Рекурсия (6 часов)
- Факториал: `factorial(n) = n * factorial(n-1)`
- Числа Фибоначчи: `fib(n) = fib(n-1) + fib(n-2)`
- Базовый случай (n <= 1)
- Глубина рекурсии (макс 1000)

Итог: `factorial(10)` → 3628800

### Спринт 4: Вывод и отладка (2 часа)
- Конверсия integer→ASCII (из asm-fundamentals)
- syscall write для вывода результата
- GDB: backtrace, inspect stack frames

Итог: Вывод результата рекурсии в stdout

## Итоговая программа: `asm_factorial`

```bash
$ ./asm_factorial 10
factorial(10) = 3628800