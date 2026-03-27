package lambda_parser;

import java.util.Optional;

/**
 * BinaryOperator это enum {add, sub, div, mul} - мат операции
 */
public class BinaryOperatorParser implements Parser{

    public enum Operation {
        ADD,
        SUB,
        MUL,
        DIV
    }
    
    @Override
    public Optional<ParseResult<Operation>> parse(String source, int begin_offset) {

        if (begin_offset > source.length()) {
            return Optional.empty();
        }
        
        return Optional.empty();
    }
}
