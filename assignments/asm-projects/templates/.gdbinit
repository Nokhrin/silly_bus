# =============================================================================
# GDB Configuration for asm-projects (x86-64 Linux)
# =============================================================================
#
# УСТАНОВКА:
#   1. Скопировать в директорию проекта
#   2. Разрешить загрузку: echo "add-auto-load-safe-path /path/to/project" >> ~/.gdbinit
#   3. Запустить GDB из директории проекта
#
# ДОСТУПНЫЕ КОМАНДЫ:
#   show-regs      - Показать все регистры
#   show-flags     - Расшифровка флагов EFLAGS
#   show-stack     - Дамп 16 значений со стека
#   show-frame     - Кадр стека: RBP, адрес возврата, аргументы
#   r              - Короткая версия show-regs
#
# ИСПОЛЬЗОВАНИЕ:
#   gdb -q ./program
#   (gdb) break _start
#   (gdb) run
#   (gdb) show-regs
#
# =============================================================================
# GDB настройки для asm-projects
set debuginfod enabled off
set disassembly-flavor intel
set confirm off
set pagination off

# Регистры
define regs
    printf "RAX:%-16lx RBX:%-16lx RCX:%-16lx RDX:%-16lx\n", $rax, $rbx, $rcx, $rdx
    printf "RSI:%-16lx RDI:%-16lx RSP:%-16lx RBP:%-16lx\n", $rsi, $rdi, $rsp, $rbp
    printf "R8:%-16lx R9:%-16lx R10:%-16lx R11:%-16lx\n", $r8, $r9, $r10, $r11
    printf "RIP:%-16lx EFLAGS:%-8lx\n", $rip, $eflags
end

# Флаги
define flags
    printf "CF:%d ZF:%d SF:%d OF:%d IF:%d\n", (($eflags>>0)&1), (($eflags>>6)&1), (($eflags>>7)&1), (($eflags>>11)&1), (($eflags>>9)&1)
end

# Стек
define stack
    printf "RSP:%lx\n", $rsp
    x/8gx $rsp
end

# Кадр стека
define frame
    printf "RBP:%lx [RBP]:%lx [RBP+8]:%lx\n", $rbp, *((long*)$rbp), *((long*)($rbp+8))
end

# Авто-запуск
define hook-run
    regs
    display/i $pc
end
