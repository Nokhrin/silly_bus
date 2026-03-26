package lambda_parser;

import java.util.function.Function;

public interface ParseResult<A> {
    A value();

    int end_offset();

    default <B> ParseResult<B> map(Function<A, B> function) {
        return null;
    }

}
