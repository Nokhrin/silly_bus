package Calculator;

import java.util.Optional;

/**
 * Напиши функцию парсинга числа , целое
 * 
 * 
 * Вот мое описание BNF
 * твоя задача задавать вопросы и предоставить BNF / eBNF грамматику для твой предметной области (переводы денег)
 * ---------------------------------------------
 * BNF - условный псевдо язык описания синтаксиса для искусственных языков
 * BNF - формальный, т.е. можно полностью автоматизировать
 * BNF - описывает как из букв/символов собрать дерево AST (abstract syntax tree)
 * BNF - описывает грамматические правила (1+)
 *
 * Правило имеет наименование и содержание
 * имя ::= содержание
 *
 * соответствует коду Optional<Result<X>> parseX( String source, int offset ); record Result<X>( X value, int beginOffset, int endOffset )
 *
 * beginOffset - начало (включительно) лексемы/некого составного значения в исходнике source
 * endOffset - конец (исключительно) лексемы/некого составного значения в исходнике source
 *
 * Содержание - указывает некий псевдо код который проверяет текст source в заданной позиции offset на соответствие ожиданию
 *
 * Содержание может состоять из 
 * - терминалов - ожидаемого текста, задается в кавычках
 * - операторов - различные способы проверки, в BNF всего два: пробел и вертикальная черта
 * - неТерминалов - это вызов соответствующего правила/функции парсинга для новой позиции
 * 
 * eBNF
 * знак или +, или -
 * цифра - строка с цифрой от 0 до 9
 * число - цифры в количестве от 1 и более
 * возможно наличие знака перед последовательностью цифр
 * 
 * sign       ::= "+" | "-"
 * digit      ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
 * digits     ::= digit { digit }*
 * signed_int ::= [ sign ] digits
 * 
 * логика парсера:  
 *
 *     Умеет обрабатывать  +,  -
 *     Останавливается на первом неправильном символе 
 *     Не парсит пустые строки 
 *     Правильно работает с границами  int
 *     Использует  Optional
 *
 */
public class IntegerLexer {
    /*
    Помещаем результат в контейнер Optional,
    цель - не выполнять явной проверки на null
    - null возможен в случае, когда лексема не найдена
    
    Семантика Optional - 
        если возвращаешь Optional.empty(), то значит - парсинг невозможен
    
     */
    
    /*
    Дженерик заменяю на конкретный тип - в случае лексического анализа целого числа - Integer
     */
    public Optional<ParseResult<Integer>> parseFrom(String source, int start) {
        if (start < 0 || start >= source.length()) {
            return Optional.empty(); // Optional.empty() => парсинг невозможен
        }

        int offset = start;
        
        // знак
        boolean isNegative = false;
        if (source.charAt(offset) == '-') {
            isNegative = true;
            offset++;
        } else if (source.charAt(offset) == '+') {
            offset++;
        }
        
        // выход за пределы строки
        if (offset >= source.length()) { return Optional.empty(); }
        
        // после знака следует цифра
        if (!Character.isDigit(source.charAt(offset))) { return Optional.empty(); }
        
        // цифры
        while (offset < source.length() && Character.isDigit(source.charAt(offset))) {
            offset++;
        }
        
        // смещение последней цифры числа
        String numStr = source.substring(start, offset);
        try {
            Integer num = Integer.parseInt(numStr);
            return Optional.of(new ParseResult<Integer>(num, start, offset - 1));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }

    }

    public static void main(String[] args) {
        IntegerLexer integerLexer = new IntegerLexer();
        Optional<ParseResult<Integer>> pr = integerLexer.parseFrom("123", 0);
        System.out.println("psvm в классе IntegerLexer");
        // получаем контейнер, вызываем геттер экземпляра, извлеченного из контейнера
        System.out.println(pr.get()); // ParseResult[value=123, start=0, end=2]
        System.out.println(pr.get().value()); // 123

        pr = integerLexer.parseFrom("-123", 0);
        System.out.println("psvm в классе IntegerLexer");
        // получаем контейнер, вызываем геттер экземпляра, извлеченного из контейнера
        System.out.println(pr.get()); // ParseResult[value=-123, start=0, end=2]
        System.out.println(pr.get().value()); // -123
    }
}
