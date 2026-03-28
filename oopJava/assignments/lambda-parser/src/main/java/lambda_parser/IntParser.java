package lambda_parser;

import java.util.Optional;

/**
 * integer = последовательность цифр, возможно со знаком. Останавливается на первом не-цифровом символе
 */
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
        
        long result = 0;

        int start = offset;

        while (offset < source.length() && Character.isDigit(source.charAt(offset))) {
            // приведение
            result = result * 10 + (source.charAt(offset) - '0');
            offset++;
        }

        // проверка переполнения
        if ((!negative && result > Integer.MAX_VALUE)) {
            return Optional.empty();
        }
        if ((negative && -result < Integer.MIN_VALUE)) {
            return Optional.empty();
        }
        

        // нет цифр
        if ((offset == start) && !negative) {
            return Optional.empty();
        }

        // применение отрицания
        if (negative) {
            result = -1 * result;
        }

        return Optional.of(new ParseResultImpl<>((int) result, offset));
    }
}
