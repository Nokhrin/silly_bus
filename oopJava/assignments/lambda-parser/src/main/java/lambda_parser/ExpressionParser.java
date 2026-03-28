package lambda_parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExpressionParser implements Parser<Combined> {

    private final Parser<Integer> intParser;
    private final Parser<String> wsParser;
    private final Parser<BinaryOperatorParser.Operation> operationParser;

    public ExpressionParser(Parser<Integer> intParser, Parser<String> wsParser, Parser<BinaryOperatorParser.Operation> operationParser) {
        this.intParser = new IntParser();
        this.wsParser = new WhitespaceParser();
        this.operationParser = new BinaryOperatorParser();
    }

    @Override
    public Optional<ParseResult<Combined>> parse(String source, int begin_offset) {

        int offset = begin_offset;
        
        // число 1
        var num1 = intParser.parse(source, offset);
        num1.ifPresentOrElse(
                parseResult -> {
                    System.out.println("Распознано Первое число: " + parseResult.value());
                },
                () -> System.out.println("Не удалось распознать Первое число")
        );
        offset = num1.get().end_offset();

        List<Suffix> tail = new ArrayList<>();

        while (true) {

            offset = 0;


            // пробелы
            var ws1 = wsParser.parse(source, offset);
            offset = ws1.get().end_offset();

            // оператор
            Optional<ParseResult<BinaryOperatorParser.Operation>> op = operationParser.parse(source, offset);
            op.ifPresentOrElse(
                    parseResult -> {
                        System.out.println("Распознана Операция: " + parseResult.value());
                    },
                    () -> System.out.println("Не удалось распознать Операцию")
            );
            offset = op.get().end_offset();

            // пробелы
            var ws2 = wsParser.parse(source, offset);
            offset = ws2.get().end_offset();

            // число 2
            var num2 = intParser.parse(source, offset);
            num2.ifPresentOrElse(
                    parseResult -> {
                        System.out.println("Распознано Первое число: " + parseResult.value());
                    },
                    () -> System.out.println("Не удалось распознать Первое число")
            );
            offset = num2.get().end_offset();

            tail.add(new SuffixImpl(op.get().value(), num2.get().value()));
        }
    }
}