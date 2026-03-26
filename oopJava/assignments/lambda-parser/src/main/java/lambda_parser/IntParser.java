package lambda_parser;

import java.util.Optional;

public class IntParser implements Parser {
    @Override
    public Optional<ParseResult<Integer>> parse(String source, int begin_offset) {
        if (begin_offset >= source.length()) {
            return Optional.empty();
        }

        int offset = begin_offset;
        boolean negative = false;

        if (source.charAt(offset) == '-') {
            negative = true;
            offset++;
            // после знака минус нет цифр
            if (offset >= source.length() || !Character.isDigit(source.charAt(offset))
            ) {
                return Optional.empty();
            }
        }

        Integer value = 0;
        int start = offset;

        while (offset < source.length() && Character.isDigit(source.charAt(offset))) {
            //приведение
            value = value * 10 + (source.charAt(offset) - '0');
            offset++;
        }

        // нет цифр
        if ((offset == start) && !negative) {
            return Optional.empty();
        }

        // применение отрицания
        if (negative) {
            int result = (int) -value;
        } else {
            int result = (int) value;
        }
        
        return Optional.of(new ParseResultImpl<>(value, offset));
    }
}
