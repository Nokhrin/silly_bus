package lambda_parser;

import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {

        Parser<Integer> parser = new IntParser();

        List<String> inputs = List.of("123", "-123", "", "asd", "0", "-0", "9999999999999999999999", "-9999999999999999999999");

        for (String input : inputs) {

            Optional<ParseResult<Integer>> integerParseResult = parser.parse(input, 0);
            integerParseResult.ifPresentOrElse(
                    parseResult -> {
                        System.out.println("Распознано число: " + parseResult.value());
                    },
                    () -> System.out.println("Не удалось распознать число в вводе")
            );

        }
    }
}

//Распознано число: 123
//Распознано число: -123
//Не удалось распознать число в вводе
//Не удалось распознать число в вводе
//Распознано число: 0
//Распознано число: 0
// TODO - переполнение
//Распознано число: -1304428545
//Распознано число: 1304428545
