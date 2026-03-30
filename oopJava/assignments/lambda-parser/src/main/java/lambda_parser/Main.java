package lambda_parser;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        Parser<Combined> parser = new ExpressionParser();
        Optional<ParseResult<Combined>> result = parser.parse("123 + 456", 0);
        
        result.ifPresentOrElse(
                res -> System.out.println(
                        "\nHEAD: " + res.value().head() 
                + "\nTAIL: " + res.value().tail().toString()),
                () -> System.out.println("Парсинг не удался")
        );
    }
}
