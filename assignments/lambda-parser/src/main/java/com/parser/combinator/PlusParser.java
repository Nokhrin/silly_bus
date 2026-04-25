package com.parser.combinator;

import com.parser.core.ParseResult;
import com.parser.core.Parser;
import com.parser.core.Plus;
import com.parser.core.Tuple2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class PlusParser<A, B> implements Plus<A, B> {
    static final Logger log = LoggerFactory.getLogger(PlusParser.class);
    private final Parser<A> parserLeft;
    private final Parser<B> parserRight;

    public PlusParser(Parser<A> parserLeft, Parser<B> parserRight) {
        this.parserLeft = parserLeft;
        this.parserRight = parserRight;
    }

    @Override
    public Optional<ParseResult<Tuple2<A, B>>> parse(String source, int begin_offset) {
        Optional<ParseResult<A>> leftResult = parserLeft.parse(source, begin_offset);
        if (leftResult.isEmpty()) return Optional.empty();
        log.debug("Успешно завершен парсинг левого элемента {}", leftResult);

        Optional<ParseResult<B>> rightResult = parserRight.parse(source, leftResult.get().end_offset());
        if (rightResult.isEmpty()) return Optional.empty();
        log.debug("Успешно завершен парсинг правого элемента {}", rightResult);

        Tuple2<A, B> tuple2 = new Tuple2<>(leftResult.get().value(), rightResult.get().value());
        log.debug("Успешно сформирован кортеж выражений: {}", tuple2);
        return Optional.of(new ParseResult<>(tuple2, rightResult.get().end_offset()));
    }

    /**
     * Возвращает парсер левого элемента
     * @return
     */
    @Override
    public Parser<A> skipRight() {
        return this.map(Tuple2::a);
    }

    /**
     * Возвращает парсер правого элемента
     * @return
     */
    @Override
    public Parser<B> skipLeft() {
        return this.map(Tuple2::b);
    }

    /**
     * Возвращает строковое представление комбинации парсеров
     * @return
     */
    @Override
    public String toString() {
        return String.format("Plus[%s -> %s]",
                parserLeft.toString(),
                parserRight.toString());
    }
}
