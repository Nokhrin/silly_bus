# === Базовые настройки ===
set debuginfod enabled off
set disassembly-flavor intel
set confirm off
set verbose off
set pagination off

# === Авто-отображение при запуске ===
define hook-run
    display/i $pc
    display/x $ss
    display/x $esp
    display/x $ebp
    display/x $rip
    display/x $rcx
    display/x $rax
    display/x $rbx
    display/x $rdx
    display/x $rsi
    display/x $rdi
end

# === Показать флаги ===
define show-flags
    info registers eflags
    printf "CF=%d (Carry)\n", (($eflags >> 0) & 1)
    printf "ZF=%d (Zero)\n", (($eflags >> 6) & 1)
    printf "SF=%d (Sign)\n", (($eflags >> 7) & 1)
    printf "OF=%d (Overflow)\n", (($eflags >> 11) & 1)
    printf "IF=%d (Interrupt)\n", (($eflags >> 9) & 1)
end

# === Показать стек ===
define show-stack
    printf "RSP=0x%lx\n", $rsp
    printf "RBP=0x%lx\n", $rbp
    x/8gx $rsp
end

# === Показать кадр стека ===
define show-frame
    printf "=== Stack Frame ===\n"
    printf "RBP=0x%lx -> [RBP]=0x%lx (старый RBP)\n", $rbp, *((long*)$rbp)
    printf "[RBP+8]=0x%lx (адрес возврата)\n", *((long*)($rbp+8))
    x/4gx $rbp
end

# === verbose запуск ===
define verbose-run
    set verbose on
    info target
    info files
    info variables
    info functions
    info proc mappings
    maintenance info sections
    break _start
    run
    info registers
    info proc all
end
