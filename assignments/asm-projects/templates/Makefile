NASM := nasm
LD := ld
GDB := gdb
READELF := readelf
OBJDUMP := objdump

BASE_NAME := step$(STEP)_test

SRC := src/$(BASE_NAME).asm
OBJ := $(BASE_NAME).o
BIN := $(BASE_NAME)

NASM_FLAGS_QUIET := -f elf64 -g
NASM_FLAGS := $(NASM_FLAGS_QUIET) -Werror -Wlabel -l $(BIN).lst
LD_FLAGS_QUIET := -m elf_x86_64
LD_FLAGS := $(LD_FLAGS_QUIET) --verbose --print-output-format --trace --cref

stepN_run:
# 	@echo "=== Сборка ($(SRC)) ==="
	@$(NASM) $(NASM_FLAGS_QUIET) $(SRC) -o $(OBJ)
# 	@echo "=== Линковка ==="
	@$(LD) $(LD_FLAGS_QUIET) $(OBJ) -o $(BIN)
# 	@echo "=== Выполнение ==="
	./$(BIN); echo $$?

stepN_test:
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

clean:
	rm -f step*
