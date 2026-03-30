package lambda_parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Выражение - цепочка "голова"
 * integer
 * +
 * опциональный "хвост"/"суффикс"
 * {[whitespace] binary_operator [whitespace] integer} 
 */
public class ExpressionParser implements Parser<Combined> {

    private final Parser<Integer> intParser;
    private final Parser<String> wsParser;
    private final Parser<BinaryOperatorParser.Operation> operationParser;

    public ExpressionParser() {
        this.intParser = new IntParser();
        this.wsParser = new WhitespaceParser();
        this.operationParser = new BinaryOperatorParser();
    }

    @Override
    public Optional<ParseResult<Combined>> parse(String source, int begin_offset) {
        if (begin_offset >= source.length()) {
            return Optional.empty();
        }
        int offset = begin_offset;
        
        // integer
        Optional<ParseResult<Integer>> headInt = this.intParser.parse(source, offset);
        if (headInt.isEmpty()) {return Optional.empty();}
        offset = headInt.get().end_offset();

        List<Suffix> suffixList = new ArrayList<>();
        while (true) {
            // {[whitespace] binary_operator [whitespace] integer}
            int wsRollback = offset;
            
            Optional<ParseResult<String>> ws1 = this.wsParser.parse(source, offset);
            offset = ws1.get().end_offset();
            
            Optional<ParseResult<BinaryOperatorParser.Operation>> op = this.operationParser.parse(source, offset);
            if (op.isEmpty()) {
                offset = wsRollback;
                break;
            }
            offset = op.get().end_offset();

            Optional<ParseResult<String>> ws2 = this.wsParser.parse(source, offset);
            offset = ws2.get().end_offset();

            Optional<ParseResult<Integer>> tailInt = this.intParser.parse(source, offset);
            if (tailInt.isEmpty()) {
                return Optional.empty(); // оператор есть, числа нет => ошибка
            }
            offset = tailInt.get().end_offset();
            
            SuffixImpl suffix = new SuffixImpl(op.get().value(), tailInt.get().value());
            suffixList.add(suffix);
        }
        
        Combined resultExpression = new CombinedImpl(headInt.get().value(), suffixList);
                
        return Optional.of(new ParseResultImpl<>(resultExpression, offset));
    }
}


/*
Почему ExpressionParser откатывает пробелы, если после них нет оператора, а не оставляет их потреблёнными — не нарушает ли это принцип «максимального совпадения»?
Отсутствие оператора после первого пробела в суффиксе - признак некорректного суффикса
Откат пробела возвращает счетчик к последнему корректному результату парсинга, то есть, сохраняет максимальное совпадение корректных токенов

Какова временная сложность парсинга выражения из N суффиксов — O(N) или хуже из-за повторных вызовов wsParser?
выполнение происходит последовательно, суффикс состоит из 2, 3 или 4 элементов
[whitespace] binary_operator [whitespace] integer
влияет линейно, так как читается каждый символ суффикса (M символов в суффиксе)
время на чтение N суффиксов = N*M - по факту это общее число символов строки, сложность линейная для  всех парсеров

Как изменить ExpressionParser, чтобы поддерживать левую рекурсию (например, для вложенных выражений в скобках) без зацикливания?
1 + ((2 + 3) + 4)
добавить проверку символов скобок, реализовать парсер атомарных выражений, например, AtomParser;  вызывать AtomParser в ExpressionParser

Зачем возвращать структуру Combined с List<Suffix>, а не сразу вычислять результат выражения — не упростит ли это код?
задача парсер - создание последовательности токенов, интерпретация токенов выполняется далее

Если суффикс требует обязательное число после оператора, как распознать унарный минус в "1 + -2" — не противоречит ли это грамматике?
грамматика не описывает унарные операторы, но описывает integer, который может быть числом с предшествующим минусом
для поддержки Integer следует поддерживать парсинг строк, представляющих значения в [Integer.MIN_VALUE; Integer.MIN_VALUE] => [-2147483648; 2147483647]
 */