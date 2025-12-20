## Вопросы



12) определить что у тебя в коде является ast

ast в коде - структура, состоящая из узлов
узел - объект, содержащий 
  - тип (NumValue, BinaryExpression),
  - значение (1, 2, *)
  - ссылки на дочерние узлы


оператор - центральный элемент выражения - корень бинарного узла
операнд - дочерний узел (левый или правый)

```
    *     // оператор - корень узла
  2   2   // операнды - дочерние узлы
```



в следующем коде

```java
    public static Optional<ParseResult<Expression>> parseMulDivExpression(String source, int start) {
    // Проверка входных данных
    if (source.isEmpty() || start < 0 || start >= source.length()) {
        return Optional.empty();
    }

    //atom_expression
    Optional<ParseResult<Expression>> atom_expressionOpt1 = parseAtomExpression(source, start);
    if (atom_expressionOpt1.isEmpty()) { return Optional.empty(); }

    Expression atom_expression1 = atom_expressionOpt1.get().value();
    int offset = atom_expressionOpt1.get().end();

    //{ [ws] mul_div_operation [ws] num_value }
    
    // цикл создает внутренний узел | ветвление AST
    // 
    while (offset < source.length()) {
        //[ws]
        Optional<ParseResult<String>> ws = parseWhitespace(source, offset);
        if (ws.isPresent()) {
            offset = ws.get().end();
        }

        //mul_div_operation
        Optional<ParseResult<Operation>> opOpt = parseMulDivOperation(source, offset);
        if (opOpt.isEmpty()) {
            //оператора нет, выражение { [ws] mul_div_operation [ws] num_value } невалидно
            break;
        }
        Operation op = opOpt.get().value();
        offset = opOpt.get().end();

        //[ws]
        ws = parseWhitespace(source, offset);
        if (ws.isPresent()) {
            offset = ws.get().end();
        }

        //num_value
        Optional<ParseResult<Expression>> atom_expressionOpt2 = parseAtomExpression(source, offset);
        if (atom_expressionOpt2.isEmpty()) {
            //числа нет, выражение { [ws] mul_div_operation [ws] num_value } невалидно
            break;
        }

        Expression atom_expression2 = atom_expressionOpt2.get().value();
        offset = atom_expressionOpt2.get().end();

        // mul_div_expression ::= num_value { [ws] mul_div_operation [ws] num_value }
        atom_expression1 = new BinaryExpression(atom_expression1, op, atom_expression2);
    }

    return Optional.of(new ParseResult<>(atom_expression1, start, offset));
}
```

инструкция `atom_expression1 = new BinaryExpression(atom_expression1, op, atom_expression2);`

`atom_expression1` - ссылка на объект `atom_expression1`, который обновляется на каждой итерации цикла

это процесс итерации с накоплением
в каждой итерации `atom_expression1` получает ссылку на новый объект выражения, 
который содержит ссылку на выражение из предыдущей итерации в качестве левого операнда , новое подвыражение - в качестве правого

построение снизу-вверх - от листьев -> к корню - ниже в примере выполняем от 1 к 5

```
5        *
4       / \
3      +   4
2     / \
1    1   2
```
1 - создание листьев 1, 2, 4
2 - создание узла +   // связка листьев 1 и 2
3 - создание узла *   // связка узла + и листа 4
4 - создание выражения *  // корень


не рекурсия, так как не происходит вызова функцией самой себя
рекурсия имела бы место в случае вызова `parseMulDivExpression` в данном примере

пусть f - процедура парсинга, которая имплицирует ast из строки source
f: source -> ast
f(s) = дерево, построенное из левоассоциативных операций
стартовое значение f(s) = num_value (число)
в каждой итерации обновляется значение f(s) = f(s) * num_value

### ast в грамматике парсера

```
add_sub_expression ::= mul_div_expression { [ws] add_sub_operation [ws] mul_div_expression }
mul_div_expression ::= atom_expression { [ws] mul_div_operation [ws] atom_expression }
atom_expression ::= num_value | '(' [ws] add_sub_expression [ws] ')'
mul_div_operation ::= "*" | "/"
add_sub_operation ::= "+" | "-"
num_value ::= [sign] digit {digit}
digit ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
sign ::= "+" | "-"
ws ::= (" " | "\t" | "\n" | "\r") {" " | "\t" | "\n" | "\r"}
```

нетерминалы - выражения - внутренние узлы - точки ветвления:
- add_sub_expression
- mul_div_expression
- atom_expression

описывают структуру выражений, 
могут породить новые ветви - новые узлы - дерева

mul_div_operation, add_sub_operation - операторы, внутренние узлы - от них ветвятся выражения

num_value - терминалы - формируют выражения - листья дерева
digit, sign - конструктивные элементы num_value

ws - не включается в структуру, не может быть листом, так как игнорируется при разборе

### представление ast в результате работы

узлы - нетерминалы - экземпляры BinaryExpression - они могут "раскрыться" в новые ветви дерева
листья - терминалы - экземпляры NumValue

```
Optional[ParseResult[value=BinaryExpression[left=
NumValue[value=9.0], op=DIV, 
                             right=BinaryExpression[
                                     left=NumValue[value=6.0], op=SUB, 
                                                   right=BinaryExpression[
                                                             left=NumValue[value=3.0], op=MUL, right=NumValue[value=3.0]]]], start=0, end=17]]
               9.0               /
                                                     6.0           -
                                                                         3.0         *           3.0
                                                                         
                                                                                    
```

```
Optional[ParseResult[value=BinaryExpression[left=
NumValue[value=9.0], op=DIV, 
                             right=BinaryExpression[
                                     left=NumValue[value=6.0], op=SUB, 
                                                   right=BinaryExpression[
                                                             left=NumValue[value=3.0], op=MUL, right=NumValue[value=3.0]]]], start=0, end=17]]
               9.0               /                                                                        // это ast от `/`, которое 
                                                     6.0           -                                      // содержит ast от `-`, которое
                                                                         3.0         *           3.0      // содержит ast от `*`, элементы которого уже не ast, но листья-терминалы
                                                                         
                                                                                    
```


каждое ветвление есть ast
ast, расположенное ранее в цепочке вызовов, содержит ast последующих вызовов

бинарное выражение
`a + (b * c)`
- бинарное дерево, где узел - `+`, листья - `a` и `(b*c)`

`(b*c)` в свою очередь так же бинарное выражение


a - выражение
b - выражение
c - выражение
b * c - выражение
(b * c) - выражение
a + (b * c) - выражение



---

13) определить где у тебя в коде лексический анализатор, и какие его функции

понимание реализации лексического анализатора

лексер - в моем коде - совокупность статических методов, 
- принимает на вход последовательность символов и стартовую позицию
- анализируют подстроку | подпоследовательность символов
- из последовательности символов формирует лексемы согласно установленным формальным лексическим правилам системы

пример - парсинг **операторов** сложения/вычитания
```java
public static Optional<ParseResult<Operation>> parseAddSubOperation(String source, int start) {
    if (source.isEmpty() || start < 0 || start >= source.length()) {
        return Optional.empty();
    }

    char op = source.charAt(start);

    return switch (op) {
        case '+' -> Optional.of(new ParseResult<>(Operation.ADD, start, start + 1));
        case '-' -> Optional.of(new ParseResult<>(Operation.SUB, start, start + 1));
        default -> Optional.empty();
    };
}
```
функции лексера:
- преобразование входной строки в последовательность лексем - числа, скобки, операторы, пробельные символы
  - в моей реализации без регулярок, "ручным" способом
- определение типа лексемы - выражение ADD, пробел и тд
- возврат смещения курсора


парсер - система уровнем выше лексера 
- принимает на вход результат работы лексера
- проверяет соответствие текста синтаксическим правилам системы
- возвращает `Optional<ParseResult<Expression>>`

пример - парсинг **выражения** 
```java
public static Optional<ParseResult<Expression>> parseAddSubExpression(String source, int start) {
//...
}
```