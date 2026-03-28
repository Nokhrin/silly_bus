package lambda_parser;

import java.util.Optional;

/**
 * Парсер односимвольного токена
 * BinaryOperator это enum {add, sub, div, mul} - мат операции
 * Недопустимые символы, ошибки => Optional.empty()
 */
public class BinaryOperatorParser implements Parser{

    public enum Operation {
        ADD,
        SUB,
        MUL,
        DIV
    }
    
    @Override
    public Optional<ParseResult<Operation>> parse(String source, int begin_offset) {

        if (begin_offset >= source.length()) {
            return Optional.empty();
        }
        
        char c = source.charAt(begin_offset);
        Operation op = switch (c) {
            case '+' -> Operation.ADD;
            case '*' -> Operation.MUL;
            case '-' -> Operation.SUB;
            case '/' -> Operation.DIV;
            default -> null;
        };
        
        if (op == null) {
            return Optional.empty();
        }
        
        return Optional.of(new ParseResultImpl<>(op, begin_offset + 1));
    }
}

/*
Почему Operation объявлен как вложенный enum, а не отдельный класс?
для сокращения количества классов?
будет ли лучшим решением создать отдельный класс с ограничением наследования? sealed
позволит избавить от default в switch
целесообразно ли?
enum Operation логически связан с парсером операций, не требует другим классам

Сколько операций сравнения выполняет mapCharToOperation() в худшем случае (символ /) и можно ли заменить switch на Map<Character, Operation>?
будет ли прямой вызов из hashmap быстрее switch? 
константное время чтения в Map?
в switch - линейное - O(n) ? - нет, O(log n) в худшем случае
хэшмап тратит время на расчет hashcode, значения не локализованы в heap
=> для малого количества вариантов (здесь 4), switch не проигрывает по скорости hashmap


Если потребуется поддержка многобайтных операторов (например, ** для возведения в степень), какое изменение сигнатуры потребуется?
потребуется добавить в операции двухсимвольные операторы, реализовать просмотр символа +1 для проверки токена оператора
при этом считать итоговым максимальное совпадение, т.е. ** как единый оператор, а не как два оператора *

Зачем возвращать Operation enum, а не Character — не упростит ли это код, перенеся маппинг на уровень выражения?
operation enum дает ограничение возможных возвращаемых значений
такое ограничение сокрщает площадь ошибки


Если символ - используется и как оператор вычитания, и как знак отрицательного числа, как парсер различает эти два случая без контекста?
первый символ соответствующий одному из значений enum будет распознан как оператор, парсер оператора завершит выполнение
контекст выражение недоступен парсеру токена, композиция токенов выполняется уровнем выше

 */