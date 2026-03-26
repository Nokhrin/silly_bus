package lambda_parser;

import java.util.Optional;

/**
 * BinaryOperator это enum {add, sub, div, mul} - мат операции
 */
public class BinOpParser implements Parser{

    public enum Operation {
        ADD,
        SUB,
        MUL,
        DIV
    }
    
    @Override
    public Optional<ParseResult<Operation>> parse(String source, int begin_offset) {
        return Optional.empty();
    }
}
