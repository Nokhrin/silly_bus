package lambda_parser;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Parser<Combined> parser = new ExpressionParser();

        List<String> inputs = List.of(
                "123",                    // только head
                "123 + 456",              // head + 1 suffix
                "123-456",                // без пробелов
                "1+2+3+4",                // множественные суффиксы
                "123 +",                  // ошибка: оператор без числа
                "9999999999999999999999"  // ошибка: переполнение
        );

        for (String input : inputs) {
            parser.parse(input, 0);
        }

    }}