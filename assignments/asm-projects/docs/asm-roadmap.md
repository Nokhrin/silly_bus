# Дорожная карта проектов asm-*

## asm-fundamentals (20 часов)
- Регистры, арифметика, память, циклы, ввод-вывод

## asm-functions (16 часов)
- Стек: call/ret, кадр стека, RBP/RSP
- ABI: аргументы (RDI-R9), возврат (RAX)
- Рекурсия: factorial, fibonacci
- Отладка: GDB backtrace, stack frames

## fastlog (48 часов)
- Обработка логов, парсинг, сортировка, кэш-локальность

## turing-machine-asm (24 часа)
- Эмуляция МТ на ассемблере
- Состояния, таблица переходов, лента, головка

## lowlevel-runtime-asm (40-50 часов)
- Стек: call/ret, ABI, выравнивание
- Куча: malloc/free, brk, детектирование утечек
- Структуры: смещения, массивы, связные списки
- Прерывания: sigaction, SIGINT, ptrace

## os-bootloader (40+ часов, факультатив)
- Real Mode, Protected Mode, GDT, paging
- Ring 0/3, прерывания, сегментация
