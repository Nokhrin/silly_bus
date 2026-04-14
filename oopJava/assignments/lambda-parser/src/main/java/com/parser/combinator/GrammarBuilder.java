package com.parser.combinator;

import com.parser.atomic.BinaryOperatorParser;
import com.parser.atomic.IntParser;
import com.parser.atomic.WhitespaceParser;
import com.parser.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * Реализация грамматики
 * integer {[whitespace] binary_operator [whitespace] integer}
 */
public class GrammarBuilder {
    static final Logger log = LoggerFactory.getLogger(GrammarBuilder.class);

    /**
     * Возвращает парсер грамматики
     */
    public static Parser<Combined> getExpressionParser() {
        Parser<Integer> integerParser = new IntParser();
        Parser<Whitespace> whitespaceParser = new WhitespaceParser();
        Parser<BinaryOperator> binaryOperatorParser = new BinaryOperatorParser();

        log.debug("Создание парсера суффикса {[whitespace] binary_operator [whitespace] integer} - начало");
        Parser<Tuple2<
                Optional<Whitespace>,
                BinaryOperator
                >> parser1 = whitespaceParser.optional().plus(binaryOperatorParser);
        Parser<Tuple2<Tuple2<
                Optional<Whitespace>,
                BinaryOperator>,
                Optional<Whitespace>
                >> parser2 =
                parser1.plus(whitespaceParser.optional());
        Parser<Tuple2<Tuple2<Tuple2<
                Optional<Whitespace>,
                BinaryOperator>,
                Optional<Whitespace>>,
                Integer
                >> finalParser =
                parser2.plus(integerParser);

        log.debug("Создание парсера суффикса - успешно завершено");

        Parser<Suffix> suffixParser = finalParser.map(tuple2 -> {
            log.debug("Преобразование кортежей в Suffix - начало");
            BinaryOperator binaryOperator = tuple2.a().a().b();
            Integer integer = tuple2.b();
            return new Suffix(binaryOperator, integer);
        });
        log.debug("Построен список суффиксов: {}", suffixParser);

        Parser<List<Suffix>> tailParser = suffixParser.repeat(0, Integer.MAX_VALUE);
        Parser<Tuple2<Integer, List<Suffix>>> expressionParser = integerParser.plus(tailParser);
        log.debug("Построен парсер выражения: {}", expressionParser);

        return expressionParser.map(tuple2 -> new Combined(tuple2.a(), tuple2.b()));
    }

}
