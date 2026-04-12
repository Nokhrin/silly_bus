package com.parser.core;

import java.util.function.Function;

/**
 * Результат успешного парсинга.
 * Ошибка парсинга -> Optional.empty()
 *
 * @param <A> тип распарсенного значения
 */
public interface ParseResult<A> {
    A value();
    int end_offset();

    /**
     * Создает результат парсинга
     * Time: O(1), Space: O(1)
     */
    static <A> ParseResult<A> of(A value, int end_offset) {
        return new ParseResult<A>() {
            @Override
            public A value() {
                return value;
            }

            @Override
            public int end_offset() {
                return end_offset;
            }
        };
    }

    /**
     * Операцию Мар/FlatМар для Parser и
     *    ParserResult
     *    Операция Мар замена содержимого контейнера.
     *    Операция должна порождать новый контейнер.
     *    В статически типизируемых языках операция Мар меняет тип содержимого:
     *    пример: List < String> на List < Boolean>
     *    Операция Мар применяется к каждому элементу Function< A,B> , кол- во элементов
     *    контейнер остается не изменным
     * @param function
     * @return
     * @param <B>
     */
    default <B> ParseResult<B> map(Function<A, B> function) {
        return null;
    }

    /**
     * Операцию Мар/FlatМар для Parser и
     *    ParserResult
     *    Операция Мар замена содержимого контейнера.
     *    Операция должна порождать новый контейнер.
     *    В статически типизируемых языках операция Мар меняет тип содержимого:
     *    пример: List < String> на List < Boolean>
     *    Операция Мар применяется к каждому элементу Function< A,B> , кол- во элементов
     *    контейнер остается не изменным
     * @param function
     * @return
     * @param <B>
     */
    default <B> ParseResult<B> flatMap(Function<A, B> function) {
        return null;
    }

    
}
