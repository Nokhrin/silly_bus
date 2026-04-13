package com.parser;

import com.parser.atomic.IntParser;
import com.parser.core.BinaryOperator;
import com.parser.core.ParseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Отладочные запуски
 */
public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Optional<ParseResult<String>> parseResult = Optional.of(new ParseResult<>("123", 0));
        log.info("value={}, offset={}", parseResult.get().value(), parseResult.get().end_offset());
        log.info(parseResult.get().map(Integer::parseInt).value().toString());
        log.info("value={}, offset={}", parseResult.get().value(), parseResult.get().end_offset());
    }
}
