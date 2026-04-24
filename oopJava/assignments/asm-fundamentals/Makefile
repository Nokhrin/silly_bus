step1_test:
	@echo "=== Сборка ==="
	nasm -f elf64 -g src/step1_task.asm -o step1_task.o
	@echo "=== Линковка ==="
	ld -no-pie -m elf_x86_64 step1_task.o -o step1_task
	@echo "=== Проверка выравнивания ==="
	readelf -lW step1_task | grep LOAD
	@echo "=== Проверка результата ==="
	@./step1_task; test $$? -eq 33 && echo "PASS: exit code 33" || echo "FAIL: expected 33, got $$?"
	@echo "=== Чистка ==="
	rm -f step1_task.o step1_task

step2_test:
	@echo "=== Сборка ==="
	nasm -f elf64 -g src/step2_task.asm -o step2_task.o
	@echo "=== Линковка ==="
	ld -no-pie -m elf_x86_64 step2_task.o -o step2_task
	@echo "=== Проверка выравнивания ==="
	readelf -lW step2_task | grep LOAD
# 	@echo "=== Дебаггер ==="
# 	gdb -q ./step2_task
	@echo "=== Проверка результата ==="
	@./step2_task; test $$? -eq 150 && echo "PASS: exit code 150" || echo "FAIL: expected 150, got $$?"
	@echo "=== Чистка ==="
	rm -f step2_task.o step2_task
