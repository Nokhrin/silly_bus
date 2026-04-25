package com.parser.atomic;

import com.parser.core.ParseResult;
import com.parser.core.Parser;
import com.parser.core.Whitespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Парсер пробельных символов.
 */
public class WhitespaceParser implements Parser<Whitespace> {

    static final Logger log = LoggerFactory.getLogger(WhitespaceParser.class);

    /**
     * Возвращает строковое представление парсера
     * @return
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

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
            log.debug("Ошибка ввода: source={0}, offset={}", source, begin_offset);
            return Optional.empty();
        }

        int offset = begin_offset;

        while (offset < source.length() && isWhitespace(source.charAt(offset))) {
            log.debug("Считан пробельный символ по смещению {}", offset);
            offset++;
        }

        if (offset == begin_offset) {
            log.debug("Отсутствуют ожидаемые пробелы");
            return Optional.empty();
        }

        return Optional.of(new ParseResult<>(Whitespace.INSTANCE, offset));
    }

}
