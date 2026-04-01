package lambda_parser;

import java.util.function.Function;

public interface ParseResult<A> {
    A value();

    int end_offset();

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
