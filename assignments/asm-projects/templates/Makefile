stepN_test:
	@echo "=== Сборка ==="
	nasm -f elf64 -g src/stepN_task.asm -o stepN_task.o
	@echo "=== Линковка ==="
	ld -no-pie -m elf_x86_64 stepN_task.o -o stepN_task
	@echo "=== Проверка выравнивания ==="
	readelf -lW stepN_task | grep LOAD
	readelf -W -S stepN_task | grep -E '\.data|\.bss'
	objdump -d stepN_task
	gdb -q ./stepN_task
	@echo "=== Чистка ==="
	rm -f stepN_task.o stepN_task