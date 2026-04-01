package lambda_parser;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface Parser<A> {
    /**
     *
     * @param source
     * @param begin_offset - признак потребления строки
     * @return
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