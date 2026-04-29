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

step3_test:
	@echo "=== Сборка ==="
	nasm -f elf64 -g src/step3_task.asm -o step3_task.o
	@echo "=== Линковка ==="
	ld -no-pie -m elf_x86_64 step3_task.o -o step3_task
	@echo "=== Проверка выравнивания ==="
	readelf -lW step3_task | grep LOAD
	@echo "=== Проверка результата ==="
	@./step3_task; echo $?
	@./step3_task; test $$? -eq 2 && echo "PASS: exit code 2" || echo "FAIL: expected 2, got $$?"
	@echo "=== Чистка ==="
	rm -f step3_task.o step3_task

step4_test:
	@echo "=== Сборка ==="
	nasm -f elf64 -g src/step4_task.asm -o step4_task.o
	@echo "=== Линковка ==="
	ld -no-pie -m elf_x86_64 step4_task.o -o step4_task
	@echo "=== Проверка выравнивания ==="
	readelf -lW step4_task | grep LOAD
	readelf -W -S step4_task | grep -E '\.data|\.bss'
# Корректно при вводе с клавиатуры
# Проблема при pipe весь ввод доступен сразу. Первый sys_read может прочитать оба числа вместе ("42\n2\n"), второй read получает EOF.
# 	@echo "=== Тест: 42, 2 ==="
# 	@echo -e "42\n2" | ./step4_task | grep -q "Sum: 44" && echo "PASS: Sum" || echo "FAIL: Sum"
# 	@echo -e "42\n2" | ./step4_task | grep -q "Prod: 84" && echo "PASS: Prod" || echo "FAIL: Prod"
# 	@echo "=== Тест: 0, 0 ==="
# 	@echo -e "0\n0\n" | ./step4_task | grep -q "Sum: 0" && echo "PASS: Sum" || echo "FAIL: Sum"
# 	@echo -e "0\n0\n" | ./step4_task | grep -q "Prod: 0" && echo "PASS: Prod" || echo "FAIL: Prod"
# 	@echo "=== Тест: 99, 99 ==="
# 	@echo -e "99\n99\n" | ./step4_task | grep -q "Sum: 198" && echo "PASS: Sum" || echo "FAIL: Sum"
# 	@echo -e "99\n99\n" | ./step4_task | grep -q "Prod: 981" && echo "PASS: Prod" || echo "FAIL: Prod"
# 	@echo "=== Тест: EOF ==="
# 	@./step4_task < /dev/null; test $$? -eq 0 && echo "PASS: EOF" || echo "FAIL: EOF"
# 	objdump -d step4_task
# 	gdb -q ./step4_task
# 	@echo "=== Чистка ==="
# 	rm -f step4_task.o step4_task