package lambda_parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 Класс реализующий Parser<List<A>> , который:
 - в конструкторе принимает Parser<А>
 - применяет принятый парсер для парсинга повторов во входных данных (source, offset)
 - Возвращает в случае совпадения результат.
 - Можно указать Минимальное /максимальное кол- во повторов (min, max)
 - Работает по „жадному" алгоритму.
 Данный Parser<List<A>> является частю синтаксиса EBNF
 В случае min = 0 & max = 1, это соответствует квадратным скобками в eBNF
 В случае min = 0 & max > 0 - фигурным скобкам еBNF
 */
public class ListParser<A> implements Parser<List<A>>{
    private final Parser<A> parser;
    private final int min;
    private final int max;

    public ListParser(Parser<A> parser, int min, int max) throws IllegalAccessException {
        if (min > max || min < 0) {
            throw new IllegalAccessException("Некорректно указаны min и max");
        }
        this.parser = parser;
        this.min = min;
        this.max = max;
    }

    @Override
    public Optional<ParseResult<List<A>>> parse(String source, int begin_offset) {
        int offset = begin_offset;
        List<A> resList = new ArrayList<>();
        int count = 0; // счетчик лексем для проверки считывания требуемого количества

        while (count < max) {
            var res = parser.parse(source, offset);
            if (res.isEmpty() ) {break;}
            int updOffset = res.get().end_offset();
            if (updOffset == offset) break; // не произошло смещения

            resList.add(res.get().value());
            offset = updOffset;
            count++;
        }

        if (count < min) return Optional.empty();
        return Optional.of(new ParseResultImpl<>(resList, offset));
    }
}
