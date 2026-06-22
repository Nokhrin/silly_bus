# Interpreter

# Stage 1: Калькулятор школьной алгебры / Algebraic calc

## Цель

Спроектировать и реализовать формальную грамматику объявлений и присваиваний

## Задачи

- Разработать EBNF-грамматику лексем и синтаксических правил для объявлений и операторов присваивания
- Реализовать парсинг с помощью ANTLR4 и генерацию AST

## Грамматика

### Свойства

- Предметная область: школьная алгебра
- Типизация: вывод типа в runtime (целые и дробные числа)
- Переменные: строгая инициализация до использования, переопределение разрешено
- Идентификаторы: `[a-zA-Z]+[0-9]*`, чувствительны к регистру
- Приоритеты (от высшего к низшему): скобки `()`, степень `^`, унарные (`+`, `-`, `| |`, `!`), умножение/деление `*/`, сложение/вычитание `+-`

### Формальные правила

```ebnf
program   ::= statement {statement}
statement ::= assignment | expression
assignment ::= id "=" expression

expression ::= sum
sum        ::= mul { ("+" | "-") mul }
mul        ::= pow { ("*" | "/") pow }
pow        ::= un [ "^" pow ]
un         ::= ["+" | "-"] fact
fact       ::= prim [ "!" ]
prim       ::= num | id | "|" expression "|" | "(" expression ")"

id         ::= letter { letter | digit }
num        ::= digit {digit} [ "." {digit} ] | "." digit {digit}

letter     ::= "a" | ... | "z" | "A" | ... | "Z"
digit      ::= "0" | "1" | ... | "9"
```

---

## Stage 2: bool

---

# Cheatsheet

```shell
# ANTLR4 в .bashrc
export ANTLR_JAR=~/.local/share/java/antlr-4.13.2-complete.jar

antlr() {
    java -jar "$ANTLR_JAR" "$@"
}

grun() {
    java -cp "$ANTLR_JAR:target/classes" org.antlr.v4.gui.TestRig "$@"
}
```

```shell
cd ~/projects/silly_bus/mylang-interpreter/
# Компиляция грамматики с плагином
mvn clean compile
# Компиляция грамматики без плагина (в /tmp, для отладки)
# see how ANTLR translates your left-recursive rules
antlr -o /tmp -Xlog src/main/antlr4/com/nokhrin/interpreter/Calc.g4
# Визуализация
# дерево в консоль
grun com.nokhrin.interpreter.Algebraic prog -tree < src/test/resources/algebra.txt
# дерево в gui
grun com.nokhrin.interpreter.Algebraic prog -gui < src/test/resources/algebra.txt
# токены
grun com.nokhrin.interpreter.Algebraic tokens -tokens < src/test/resources/algebra.txt
```

