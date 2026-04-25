ASM=nasm
LD=ld
CFLAGS=-f elf64
LDFLAGS=-no-pie -m elf_x86_64

build: main.o
	$(LD) $(LDFLAGS) main.o -o fastlog
main.o: main.asm
	$(ASM) $(CFLAGS) main.asm -o main.o
run: build
	./fastlog < test.log > output.log
clean:
	rm -f main.o fastlog
test: build
	@echo "=== Smoke test: exit code ==="
	@./fastlog < /dev/null; test $$? -eq 0 && echo "PASS" || echo "FAIL"
	@echo "=== Strace verification ==="
	@strace -e exit_group -q ./fastlog < /dev/null 2>&1 | grep -q "exit_group(0)" && echo "PASS" || echo "FAIL"