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
public class IntegerLexerDraft {
    /*
    Помещаем результат в контейнер Optional,
    цель - не выполнять явной проверки на null
    - null возможен в случае, когда лексема не найдена
    
    Семантика Optional - 
        если возвращаешь Optional.empty(), то значит - парсинг невозможен

    Тип для value класса ParseResult - обобщение/дженерик
    Дженерик заменяю на конкретный тип - в данном случае - Integer
     */

    /**
     * главный метод
     */
    public Optional<ParseResult<Integer>> parseFrom(String source, int start) {
        int offset = start;
        
        // знак
        Optional<Boolean> signContainer = parseSign(source, offset);
        // смещение, если знак представлен символом
        if (!signContainer.isEmpty()) {
            offset++;
        }
        
        // цифры
        Optional<ParseResult<Integer>> digitsContainer = parseDigits(source, offset);

        // цифр нет => парсинг не удался
        if (digitsContainer.isEmpty()) { return Optional.empty(); }

        int finalOffset = offset;  // временная переменная для лямбда-вызовов
        return signContainer.flatMap(sign -> {
            // если знак есть, сместить курсор
            int flatOffset = finalOffset + 1;
            return parseDigits(source, flatOffset).flatMap(digits -> {
                int value = digits.value();
                if (sign) {
                    value = -value;
                }
                return Optional.of(new ParseResult<>(value, digits.start(), digits.end()));
            });
        });
    }

    /**
     * парсинг знака
     */
    public Optional<Boolean> parseSign(String source, int offset) {
        // + | -
        char ch = source.charAt(offset);
        // N >= 0 -> положительное -> true
        // N < 0 -> отрицательное -> false
        // знака нет => empty
        return switch (ch) {
            case '+' -> Optional.of(true);
            case '-' -> Optional.of(false);
            default -> Optional.empty();
        };

    }

    /**
     * ПОПЫТКА парсинга цифр
     */
    public Optional<ParseResult<Integer>> parseDigits(String source, int start) {
        int offset = start;

        // выход за пределы строки
        if (offset >= source.length()) { return Optional.empty(); }
        
        // нет цифр
        if (offset == source.length()) { return Optional.empty(); }

        // цифры
        while (offset < source.length() && Character.isDigit(source.charAt(offset))) {
            offset++;
        }
        
        //
        // ??? использование parseInt допускается условиями задачи?
        //
        String numStr = source.substring(start, offset);
        try {
            Integer num = Integer.parseInt(numStr);
            return Optional.of(new ParseResult<Integer>(num, start, offset - 1));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }

    }

    public static void main(String[] args) {
        IntegerLexerDraft integerLexerDraft = new IntegerLexerDraft();
        Optional<ParseResult<Integer>> pr = integerLexerDraft.parseFrom("123", 0);
        System.out.println("psvm в классе IntegerLexerDraft");
        // получаем контейнер, вызываем геттер экземпляра, извлеченного из контейнера
        System.out.println(pr.get()); // ParseResult[value=123, start=0, end=2]
        System.out.println(pr.get().value()); // 123

        pr = integerLexerDraft.parseFrom("-123", 0);
        System.out.println("psvm в классе IntegerLexerDraft");
        // получаем контейнер, вызываем геттер экземпляра, извлеченного из контейнера
        System.out.println(pr.get()); // ParseResult[value=-123, start=0, end=2]
        System.out.println(pr.get().value()); // -123
    }
}
