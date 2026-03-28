package lambda_parser;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        // парсинг простого выражения `integer ws? op ws? integer`
        Parser<Integer> intParser = new IntParser();
        Parser<String> wsParser = new WhitespaceParser();
        Parser<BinaryOperatorParser.Operation> operationParser = new BinaryOperatorParser();

        List<String> inputsExpressions = List.of("123 + 456", "123-456", "123* 456", "123 /456", "0+0 ", "999999999-999999999");

        int offset;
        for (String input : inputsExpressions) {

            offset = 0;

            // число 1
            var num1 = intParser.parse(input, offset);
            num1.ifPresentOrElse(
                    parseResult -> {
                        System.out.println("Распознано Первое число: " + parseResult.value());
                    },
                    () -> System.out.println("Не удалось распознать Первое число")
            );
            offset = num1.get().end_offset();

            // пробелы
            var ws1 = wsParser.parse(input, offset);
            offset = ws1.get().end_offset();

            // оператор
            var op = operationParser.parse(input, offset);
            op.ifPresentOrElse(
                    parseResult -> {
                        System.out.println("Распознана Операция: " + parseResult.value());
                    },
                    () -> System.out.println("Не удалось распознать Операцию")
            );
            offset = op.get().end_offset();

            // пробелы
            var ws2 = wsParser.parse(input, offset);
            offset = ws2.get().end_offset();

            // число 2
            var num2 = intParser.parse(input, offset);
            num2.ifPresentOrElse(
                    parseResult -> {
                        System.out.println("Распознано Первое число: " + parseResult.value());
                    },
                    () -> System.out.println("Не удалось распознать Первое число")
            );
            offset = num2.get().end_offset();

            System.out.printf("Строка %s распознана как: %d %s %d%n",
                    input,
                    num1.get().value(),
                    op.get().value(),
                    num2.get().value());
        }
    }
}

/*
Распознано Первое число: 123
Распознана Операция: ADD
Распознано Первое число: 456
Строка 123 + 456 распознана как: 123 ADD 456
Распознано Первое число: 123
Распознана Операция: SUB
Распознано Первое число: 456
Строка 123-456 распознана как: 123 SUB 456
Распознано Первое число: 123
Распознана Операция: MUL
Распознано Первое число: 456
Строка 123* 456 распознана как: 123 MUL 456
Распознано Первое число: 123
Распознана Операция: DIV
Распознано Первое число: 456
Строка 123 /456 распознана как: 123 DIV 456
Распознано Первое число: 0
Распознана Операция: ADD
Распознано Первое число: 0
Строка 0+0  распознана как: 0 ADD 0
Распознано Первое число: 999999999
Распознана Операция: SUB
Распознано Первое число: 999999999
Строка 999999999-999999999 распознана как: 999999999 SUB 999999999
 */