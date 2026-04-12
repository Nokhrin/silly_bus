package com.parser.core;

import java.util.Optional;
import java.util.function.Function;

/**
 * Функциональный интерфейс парсера.
 *
 * @param <A> тип результата успешного парсинга
 */
public interface Parser<A> {
    /**
     *
     * @param source входная строка
     * @param begin_offset смещение начала парсинга
     * @return Optional.empty() если парсинг неудачен, иначе ParseResult со значением и новым offset
     *
     * Time: зависит от реализации, Space: O(1) для атомарных парсеров
     */
    Optional<ParseResult<A>> parse(String source, int begin_offset);

    /**
     * Операцию Мар/FlatМар для Parser и
     * ParserResult
     * Операция Мар замена содержимого контейнера.
     * Операция должна порождать новый контейнер.
     * В статически типизируемых языках операция Мар меняет тип содержимого:
     * пример: List < String> на List < Boolean>
     * Операция Мар применяется к каждому элементу Function< A,B> , кол- во элементов
     * контейнер остается не изменным
     *
     * @param function
     * @param <B>
     * @return
     */
    default <B> Parser<B> map(Function<A, B> function) {
        return null;
    }

    /**
     * Операцию Мар/FlatМар для Parser и
     * ParserResult
     * Операция Мар замена содержимого контейнера.
     * Операция должна порождать новый контейнер.
     * В статически типизируемых языках операция Мар меняет тип содержимого:
     * пример: List < String> на List < Boolean>
     * Операция Мар применяется к каждому элементу Function< A,B> , кол- во элементов
     * контейнер остается не изменным
     *
     * @param function
     * @param <B>
     * @return
     */
    default <B> Parser<B> flatMap(Function<A, B> function) {
        return null;
    }

}


/*
определение default <B> Parser<B> map(Function<A, B> function) означает (первое, наивное, прочтение):
"объявляется метод по умолчанию (не требующий переопределения), возвращающий пару (объект типа B; парсер, принимающий параметр типа B);
имя метода map; метод содержит параметр function, принимающий значение типа Function, представляющее интерфейс, принимающий два параметра"


 */