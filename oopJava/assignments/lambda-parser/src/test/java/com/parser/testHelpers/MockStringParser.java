package com.parser.testHelpers;

import com.parser.core.ParseResult;
import com.parser.core.Parser;

import java.util.Optional;

/**
 * Парсер String.
 * Отсутствует в ТЗ, требуется для отладки
 */
public record MockStringParser(String mockString) implements Parser<String> {

    @Override
    public Optional<ParseResult<String>> parse(String source, int begin_offset) {
        return Optional.of(new ParseResult<>(mockString, begin_offset + mockString.length()));
    }
}
