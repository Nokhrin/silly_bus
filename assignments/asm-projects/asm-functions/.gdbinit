# === Базовые настройки ===
set debuginfod enabled off
set disassembly-flavor intel
set confirm off
set verbose off
set pagination off

# === Точки останова ===
#break *0x401000+0x00000000
break _start
break _end

# === Авто-отображение при запуске ===
define hook-run
    display/i $pc
    display/x $ss
    display/x $rax
    display/x $rbx
    display/x $rcx
    display/x $rdx
    display/x $rdi
    display/x $rsi
    display/x $rip
    display/x $rsp
    display/x $rbp
    display/x $r8
    display/x $r9
    display/x $r10
    display/x $r11
    display/x $r12
end

# === Показать флаги ===
define sfl
    info registers eflags
    printf "CF=%d (Carry)\n", (($eflags >> 0) & 1)
    printf "ZF=%d (Zero)\n", (($eflags >> 6) & 1)
    printf "SF=%d (Sign)\n", (($eflags >> 7) & 1)
    printf "OF=%d (Overflow)\n", (($eflags >> 11) & 1)
    printf "IF=%d (Interrupt)\n", (($eflags >> 9) & 1)
end

# === Показать стек ===
define ss
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

# === форматирование ===
# Печать регистров с заполнением нулями
define pr32
    printf "EAX: 0x%08x  EBX: 0x%08x  ECX: 0x%08x  EDX: 0x%08x\n", $eax, $ebx, $ecx, $edx
    printf "ESI: 0x%08x  EDI: 0x%08x  ESP: 0x%08x  EBP: 0x%08x\n", $esi, $edi, $esp, $ebp
end

define pr64
    printf "RAX: 0x%016x  RBX: 0x%016x  RCX: 0x%016x  RDX: 0x%016x\n", $rax, $rbx, $rcx, $rdx
    printf "RSI: 0x%016x  RDI: 0x%016x  RSP: 0x%016x  RBP: 0x%016x\n", $rsi, $rdi, $rsp, $rbp
end

define pr8
    printf "AL: 0x%02x  AH: 0x%02x  BL: 0x%02x  BH: 0x%02x\n", $al, $ah, $bl, $bh
end