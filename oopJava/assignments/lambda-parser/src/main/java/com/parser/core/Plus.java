package com.parser.core;

/**
 * Парсер с возможностью исключения левого и/или правого элемента
 * @param <A>
 * @param <B>
 */
public interface Plus<A, B> extends Parser<Tuple2<A, B>>{

    /**
     * Исключает правый элемент
     * Возвращает левый элемент
     */
    Parser<A> skipRight();

    /**
     * Исключает левый элемент
     * Возвращает правый элемент
     */
    Parser<B> skipLeft();
}
