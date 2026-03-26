package lambda_parser;

import java.util.Optional;

/**
 * - в конструкторе принимает Parser< А>
 *
 * - применяет принятый парсер для парсинга повторов во входных данных ( source,
 *   offset)
 * - Возвращает в случае совпадения результат.
 * - Можно указать Минимальное /максимальное кол- во повторов (min, max)
 * - Работает по „жадному" алгоритму.
 *   Данный Parser < List < A > > является частю синтаксиса E BNF
 *   В случае min = 0 & max = 1, это соответствует квадратным скобками в eBNF
 *   В случае min = 0 & max > 0 - фигурным скобкам еBNF
 */
public class ListParser implements Parser{
    @Override
    public Optional<ParseResult> parse(String source, int begin_offset) {
        return Optional.empty();
    }
}
