NASM := nasm
LD := ld
GDB := gdb
READELF := readelf
OBJDUMP := objdump
#
# BASE_NAME := step$(STEP)_test
#
# SRC := src/$(BASE_NAME).asm
# OBJ := $(BASE_NAME).o
# BIN := $(BASE_NAME)

NASM_FLAGS_QUIET := -f elf64 -g
NASM_FLAGS := $(NASM_FLAGS_QUIET) -Werror -Wlabel -l $(BIN).lst
LD_FLAGS_QUIET := -m elf_x86_64
LD_FLAGS := $(LD_FLAGS_QUIET) --verbose --print-output-format --trace --cref


BASE_NAME := sum_example
SRC := src/$(BASE_NAME).asm
OBJ := $(BASE_NAME).o
BIN := $(BASE_NAME)

step_test_sum:
	@echo "=== Сборка ($(SRC)) ==="
	$(NASM) $(NASM_FLAGS) $(SRC) -o $(OBJ)
	@echo "=== Линковка ==="
	$(LD) $(LD_FLAGS) $(OBJ) -o $(BIN)
	@echo "=== Проверка выравнивания ==="
	$(READELF) -aW $(BIN) | grep -E 'LOAD|\.data|\.bss' || true
	@echo "=== Дизассемблирование ==="
	$(OBJDUMP) -d $(BIN)
	@echo "=== Отладка ==="
	$(GDB) -q ./$(BIN)

step_test:
	nasm -f elf64 src/lib_string.asm -o lib.o
	nasm -f elf64 src/main_entry.asm -o main.o
	ld lib.o main.o -o asm_string_app
	@echo "=== Проверка выравнивания ==="
	$(READELF) -aW asm_string_app | grep -E 'LOAD|\.data|\.bss' || true
	@echo "=== Дизассемблирование ==="
	$(OBJDUMP) -d asm_string_app
	@echo "=== Отладка ==="
	$(GDB) -q ./asm_string_app


clean:
	rm -f step*
