# === Переменные ===
NASM := nasm
LD := ld
GDB := gdb
READELF := readelf
OBJDUMP := objdump

NASM_FLAGS := -f elf64 -g -Werror -Wlabel
LD_FLAGS := -no-pie -m elf_x86_64 --verbose --print-output-format --trace --cref

BASE_NAME := step$(STEP)_test

SRC := src/$(BASE_NAME).asm
OBJ := $(BASE_NAME).o
BIN := $(BASE_NAME)

step1_test:
	$(MAKE) STEP=1 stepN_test

step2_test:
	$(MAKE) STEP=2 stepN_test

stepN_test:
	@echo "=== Сборка ($(SRC)) ==="
	$(NASM) $(NASM_FLAGS) $(SRC) -o $(OBJ)
	@echo "=== Линковка ==="
	$(LD) $(LD_FLAGS) $(OBJ) -o $(BIN)
	@echo "=== Проверка выравнивания ==="
	$(READELF) -lW $(BIN) | grep LOAD
	$(READELF) -W -S $(BIN) | grep -E '\.data|\.bss'
	@echo "=== Дизассемблирование ==="
	$(OBJDUMP) -d $(BIN)
	@echo "=== Отладка ==="
	$(GDB) -q ./$(BIN)

clean:
	rm -f step*_test.o step*_test
#
# step1_test:
# 	@echo "=== Сборка ==="
# 	nasm -f elf64 -g -Werror -Wnumber -Wmacro -Wlabel \
# 		-Worphan-labels -Wuser -Wbad-pragma \
# 		-l step1_concept_call_ret.lst \
# 		-M step1_concept_call_ret.dep \
# 		src/step1_concept_call_ret.asm -o step1_concept_call_ret.o
# # 	nasm -f elf64 -g src/step1_concept_call_ret.asm -o step1_concept_call_ret.o
# 	@echo "=== Линковка ==="
# 	ld -no-pie -m elf_x86_64 \
# 	--verbose \
# 	--print-output-format \
# 	--trace \
# 	--cref \
# 	--map=step1_concept_call_ret.map \
# 	step1_concept_call_ret.o -o step1_concept_call_ret
# # 	ld -no-pie -m elf_x86_64 step1_concept_call_ret.o -o step1_concept_call_ret
# 	@echo "=== Проверка выравнивания ==="
# 	readelf -lW step1_concept_call_ret | grep LOAD
# 	readelf -W -S step1_concept_call_ret | grep -E '\.data|\.bss'
# 	objdump -d step1_concept_call_ret
# 	gdb -q ./step1_concept_call_ret
# # 	./step1_concept_call_ret
# # 	@echo "=== Чистка ==="
# # 	rm -f step1_concept_call_ret.o step1_concept_call_ret
