step1_test:
	nasm -f elf64 -g src/step1_task.asm -o step1_task.o
	ld -no-pie -m elf_x86_64 step1_task.o -o step1_task
	@./step1_task; test $$? -eq 33 && echo "PASS: exit code 33" || echo "FAIL: expected 33, got $$?"
	rm -f step1_task.o step1_task