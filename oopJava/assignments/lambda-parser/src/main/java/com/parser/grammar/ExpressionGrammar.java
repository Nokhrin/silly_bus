package com.parser.grammar;

import com.parser.atomic.BinaryOperatorParser;
import com.parser.atomic.IntParser;
import com.parser.atomic.WhitespaceParser;
import com.parser.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Описывает правила композиции грамматики
 * Expression  = Integer { Suffix } ;
 * Suffix      = [Whitespace] BinaryOperator [Whitespace] Integer ;
 * Integer     = digit { digit } ;
 * Whitespace  = ' ' | '\t' | '\n' | '\r' ;
 * BinaryOperator = '+' | '-' | '*' | '/' ;
 */
public class ExpressionGrammar {
    static final Logger log = LoggerFactory.getLogger(ExpressionGrammar.class);

    /**
     * Возвращает парсер грамматики
     */
    public static Parser<Combined> build() {
        Parser<Integer> intParser = new IntParser();
        Parser<Whitespace> wsParser = new WhitespaceParser();
        Parser<BinaryOperator> boParser = new BinaryOperatorParser();

        log.debug("Создание парсера суффикса [whitespace] binary_operator [whitespace] integer => Suffix");
        Parser<Suffix> suffixParser =
                wsParser.optional()      // Opt(ws)
                        .plus(boParser)  // Tuple2 ( Opt(ws), bo )
                        .skipLeft()      // bo
                        .plus(
                                wsParser.optional()    // Opt(ws)
                                        .plus(intParser) // Tuple2 ( Opt(ws), int )
                                        .skipLeft()     // int
                        ) // Tuple2( bo, int )
                        .map(tuple -> new Suffix(tuple.a(), tuple.b()));
        log.debug("Создан парсер суффикса: {}", suffixParser);

        log.debug("Создание парсера { Suffix } => tail");
        Parser<List<Suffix>> tailParser = suffixParser.repeat(0, Integer.MAX_VALUE);
        log.debug("Создан парсер tail: {}", tailParser);

        log.debug("Создание парсера Integer { Suffix } => head tail");
        Parser<Combined> expressionParser = intParser.plus(tailParser)
                        .map(tuple2 -> new Combined(tuple2.a(), tuple2.b()));
        log.debug("Создан парсер head tail: {}", expressionParser);

        return expressionParser;
    }

}
