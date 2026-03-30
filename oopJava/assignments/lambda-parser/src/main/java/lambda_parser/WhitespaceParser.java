package lambda_parser;

import java.util.Optional;

/**
 * whitespace = последовательно ноль или более символов E {\n, \t, \t, " "}
 */
public class WhitespaceParser implements Parser {
    private boolean isWhitespace(char c) {
        return c == ' ' || c == '\r' || c == '\n' || c == '\t';
    }

    @Override
    public Optional<ParseResult<String>> parse(String source, int begin_offset) {
        if (begin_offset > source.length()) {
            return Optional.empty();
        }

        int offset = begin_offset;
        while (offset < source.length() && isWhitespace(source.charAt(offset))) {
            offset++;
        }

        return Optional.of(new ParseResultImpl<>(source.substring(begin_offset, offset), offset));
    }


}
