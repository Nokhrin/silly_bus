package com.parser;

import com.parser.atomic.BinaryOperatorParser;
import com.parser.atomic.IntParser;
import com.parser.atomic.WhitespaceParser;
import com.parser.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Отладочные запуски
 */
public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        var intParser = new IntParser();
        var wsParser = new WhitespaceParser();
        var boParser = new BinaryOperatorParser();

        log.debug("Структура: {}", wsParser.optional().plus(boParser));
        log.debug("Структура: {}", wsParser.optional().plus(boParser).skipLeft());

        String input = "1 + 2";
        log.info("Сборка суффикса: [ws] bo [ws] int, ввод: `{}`", input);
        Parser<Suffix> suffixParser = wsParser.optional().plus(boParser).skipLeft()
                .plus(wsParser.optional().plus(intParser).skipLeft())
                .map(tuple2 -> new Suffix(tuple2.a(), tuple2.b()));
        log.info("Выполнение парсера: {}", suffixParser);
        Optional<ParseResult<Suffix>> result = suffixParser.parse(input, 0);

        log.info("Результат парсинга: {}", result);
    }
}
