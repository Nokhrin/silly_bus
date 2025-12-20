# Формальное определение КС-грамматики арифметических выражений с унарными операциями

> Документ определяет контекстно-свободную грамматику арифметических выражений, применяемую в проекте CalcParser

Приоритет: unary_operation выше, чем add_sub_operation
Ассоциативность: unary_operation - справа налево, add_sub_operation - слева направо

```ebnf
add_sub_expression ::= mul_div_expression { [ws] add_sub_operation [ws] mul_div_expression }
mul_div_expression ::= atom_expression { [ws] mul_div_operation [ws] atom_expression }
atom_expression ::= num_value 
                 | [ws] unary_operation [ws] atom_expression [ws]
                 | '(' [ws] add_sub_expression [ws] ')'
unary_operation ::= "+" | "-"
mul_div_operation ::= "*" | "/"
add_sub_operation ::= "+" | "-"
num_value ::= [sign] digit {digit}
sign ::= "+" | "-"
digit ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
ws ::= (" " | "\t" | "\n" | "\r") {" " | "\t" | "\n" | "\r"}
```



---

## Понимание проблемы использования знака как части числа

### КС-грамматика с применением терминала знака числа sign
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

проблема данной грамматики - правила 
```
num_value ::= [sign] digit {digit}
sign ::= "+" | "-"
```
определяют знак как часть числа

существует смешивание синтаксических элементов - "число со знаком", "оператор сложения", "оператор вычитания" 
в математике +1 - унарный плюс, -1 - унарный минус - это выражения
в текущих правилах строки `+1` и `-1` - целочисленные литералы

с точки зрения математики и формальной логики `+1`, `-2` - унарные операции над числами, не числа
`+1`, `-2` - знак цифра* - число, не выражение

sign должен быть либо частью числа, либо оператором - для непротиворечивости
  по правилу `num_value ::= [sign] digit {digit}` + и - определены как символы в составе числа
  по правилу `add_sub_operation ::= "+" | "-" ` + и - определены как операторы  
    => отделить символы + и от определения числа

последовательность sign не описана правилами: 
`--1` по математике `--` - два унарных минуса - дает `+1`, в текущей грамматике `--1` - невалидная форма
семантическая неоднозначность
    => добавить обработку цепочки унарных операторов

sign часть числа 
  `-1 + 2` дает `1`, а не  `-3` с учетом принятого приоритета операций
  в текущих правилах приоритет знака является частью числа, поэтому невозможно учесть его приоритет
  нельзя проанализировать знак числа, потому что знак семантически неотделим от числа
    => добавить учет приоритета унарных операций

на практике - в текущей реализии унарные операции не обрабатываются 
```java
    public static void main(String[] args) {
        Optional<ParseResult<Expression>> exp;
        
        exp = parseAddSubExpression("-1 + 2", 0);
        System.out.println(exp.get().value().evaluate());
        // 1.0
        exp = parseAddSubExpression("--1 + 2", 0);
        System.out.println(exp);
        // Optional.empty
        exp = parseAddSubExpression("--1 + +2", 0);
        System.out.println(exp);
        // Optional.empty
    }
```

решение - ввести выражение унарной операции `unary_operation ::= "+" | "-"` => интерпретировать `+1` как выражение
