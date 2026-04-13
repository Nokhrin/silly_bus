package com.parser.atomic;

import com.parser.core.ParseResult;
import com.parser.core.Parser;
import com.parser.core.Whitespace;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Парсер пробельных символов.
 */
public class WhitespaceParser implements Parser<Whitespace> {

    private static final Logger logger = Logger.getLogger(WhitespaceParser.class.getName());

    /**
     * Определение пробельных символов
     * Time: O(1)
     */
    boolean isWhitespace(char c) {
        return c == ' ' ||
                c == '\t' ||
                c == '\n' ||
                c == '\r';
    }

    /**
     * Парсит последовательность пробельных символов
     * @param source входная строка
     * @param begin_offset смещение начала парсинга
     * @return объект Whitespace, если пробельных символов >= 1, иначе Optional.empty()
     */
    @Override
    public Optional<ParseResult<Whitespace>> parse(String source, int begin_offset) {
        if (source == null || begin_offset < 0 || begin_offset >= source.length()) {
            logger.log(Level.FINE, "Ошибка ввода: source={0}, offset={1}",
                    new Object[]{source, begin_offset});
            return Optional.empty();
        }

        int offset = begin_offset;

        while (offset < source.length() && isWhitespace(source.charAt(offset))) {
            logger.log(Level.FINEST, "Считан пробельный символ по смещению {0}", offset);
            offset++;
        }

        if (offset == begin_offset) {
            logger.log(Level.FINE, "Отсутствуют ожидаемые пробелы");
            return Optional.empty();
        }

        return Optional.of(new ParseResult<>(Whitespace.INSTANCE, offset));
    }
}
