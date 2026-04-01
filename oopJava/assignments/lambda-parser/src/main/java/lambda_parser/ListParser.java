package lambda_parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Коллектор парсеров
 * - применяет принятый парсер для парсинга повторов во входных данных ( source, offset)
 * - в конструкторе принимает Parser< А>
 * - Возвращает в случае совпадения результат.
 * - Можно указать Минимальное /максимальное кол- во повторов (min, max)
 * - Работает по „жадному" алгоритму.
 * Данный Parser < List < A > > является частю синтаксиса E BNF
 * В случае min = 0 & max = 1, это соответствует квадратным скобками в eBNF
 * В случае min = 0 & max > 0 - фигурным скобкам еBNF
 */
public class ListParser<A> implements Parser<List<A>> {
    private final Parser<A> parser;
    private final int min;
    private final int max;

    public ListParser(Parser<A> parser, int min, int max) {
        this.parser = parser;
        this.min = min;
        this.max = max;
    }

    @Override
    public Optional<ParseResult<List<A>>> parse(String source, int begin_offset) {
        List<A> results = new ArrayList<>();
        int offset = begin_offset;

        for (int repeatCount = 0; repeatCount < this.max; repeatCount++) {
            Optional<ParseResult<A>> parseResult = this.parser.parse(source, offset);

            // какое поведение ожидается?
            // "123 456 abc" => "ошибка" ?
            // "123 456 abc" => "123 456" ?

            // сейчас - "123 456 abc" => "123 456"
            if (parseResult.isEmpty()) {
                break;
            }
            // при передаче парсера пробелов возможно зацикливание
            if (parseResult.get().end_offset() == offset) {
                break;
            }

            results.add(parseResult.get().value());
            offset = parseResult.get().end_offset();
        }


        if (results.size() < min) {
            return Optional.empty();
        }

        return Optional.of(new ParseResultImpl<>(results, offset));
    }
}

/*
как читать и понимать дженерик <A>  в ListParser?
почему указывается в типах при описании типа класса, типа параметра, типа возвращаемого значения, почему недостаточно ListParser? какую пользу добавляет <A>?
<A> - это любой тип , а ParseResult<A> - это результат парсинга любого типа - т.е. считать валидным в результате String, Integer, List<Integer>, ... 
какой практический толк от такого обобщения? 
возможность передать любой объект?
тогда зачем типизировать в принципе?

в данной задаче выполняется парсинг строки; почему Parser<A> , а не Parser<String> ?

ParseResult<List<A>>,  A - тип результата парсера из списка
Integer, Operation, String (пробелы), Combined, List<A>

Object можно считать "супердженериком" ?




жадность алгоритма - обработать все валидные суффиксы? 
если выражение "голова , валидный суффикс 1, валидный суффикс 2, невалидный суффикс 3, валидный суффикс 4"
суффикс 4 будет обработан?

гипотеза - нет, жадность - принимать все корректные суффиксы до первой неудачи парсинга


в чем суть проблемы Raw use of parameterized class 'Parser' ?
слишком общее применение типа? экземпляры Parser без уточнения типа принимаемого параметра? (например, Parser<String> ?)
указываем компилятору тип параметра Parser (Parser<Integer> вместо Parser)

в чем суть проблемы Class 'Parser<A>' is exposed outside its defined visibility scope ?
interface должен быть public? почему?


зачем пустые скобки в инициализаторе new ListParser<> ?
Сокращаяет запись
ListParser<Integer> listParser = new ListParser<Integer>
в
ListParser<Integer> listParser = new ListParser<>
;
подразумевает, что тип параметра в конструкторе соответствует типу переменной?
 */