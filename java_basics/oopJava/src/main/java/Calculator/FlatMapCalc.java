package Calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * План освоения такой
 *
 * Осваиваещь flatmap доя optional
 *
 * 1. Фильтрация через flatmap
 * 2. Преобразование типа параметра через flatmap
 * 3. Цепочка вызовов flatmap
 * 
 * Потом
 *
 * Комбинируешь прасеры вместе
 * 1. Последовательность
 * 2. Ветвления
 * 3. Повторы
 * 4. Рекурсия
 * 
 * 
 * Задача - применить 
 *  - функциональный подход
 *  - Optional
 *  - flatMap
 *  - filter
 *  
 * Практика - реализация калькулятора с поддержкой
 *  - игнорирования пробелов
 *  - обработки целых чисел
 *  - выполнения сложения и вычитания
 *  
 *  на вход поступает строка вида "пробел* цифра+ пробел* +|- пробел* цифра+ пробел*"
 *  примеры: "   2 -     1 ", "-5+6", "1 + 1", "-2   -  3   ", ...
 *  на выход передается Optional<Integer>, при ошибке - Optional.empty()
 *      
 * концепты
 *  - Optional - контейнер для результата, который может быть пустым,
 *      Optional - чтобы избежать проверки на null при парсинге чисел и операторов
 *  - filter - фильтрация и валидация лексем
 */
public class FlatMapCalc {

    /**
     * Удаляю пробелы
     * В переданной строке сохранить последовательность символов, исключить пробелы
     * т.к. пробелы не влияют на вычисление
     * @param str
     * @return
     */
    public static String removeSpaces(String str) {
        return str.chars() // строка -> IntStream - коды символов
                .filter(ch -> ch != ' ') // пробел ' ' неявно конвертируется в int, сравнивается с int кодом символа из потока
                .collect(
                        StringBuilder::new, // создать стрингбилдер
                        StringBuilder::appendCodePoint, // добавить char как юникод символ
                        StringBuilder::append // добавить int код в билдер
                        ).toString();  // привести к строке
    }

    /**
     * Получаю токены выражения
     * Проверяю синтаксис
     * При ошибке возвращаю Optional.empty()
     * @param expression
     * @return
     */
    public static Optional<List<String>> getTokens(Optional<String> expression) {
        if (expression.isEmpty()) { return Optional.empty(); }
        
        String sourceExpression = expression.get(); // позволяет не проверять null
        if (sourceExpression.isEmpty()) { return Optional.empty(); }
        
        List<String> tokens = new ArrayList<>();  // формируемый список токенов
        StringBuilder numBuilder = new StringBuilder(); // строка для хранения числа
        
        for (int i = 0; i < sourceExpression.length(); i++) {
            char currChar = sourceExpression.charAt(i);
            
            if (currChar == '+' || currChar == '-') { // оператор
                // если знак, значит, далее новое число, добавляем в лексемы и удаляем первое число
                if (!numBuilder.isEmpty()) {
                    tokens.add(numBuilder.toString());
                    numBuilder.setLength(0);
                }
                tokens.add(String.valueOf(currChar));
            } else if (Character.isDigit(currChar)) { // цифра
                numBuilder.append(currChar);
            } else {
                return Optional.empty(); // невалидный символ
            }
        }
        
        // второе число
        if (!numBuilder.isEmpty()) { tokens.add(numBuilder.toString()); }
        
        // по условию количество токенов нечетное и не меньше трёх
        if (tokens.size() < 3 || tokens.size() % 2 == 0) { return Optional.empty(); }
        
        return Optional.of(tokens);
    }

    public static Optional<Integer> calcTokens(Optional<List<String>> tokens) {
        // todo
        return Optional.empty();
    }
    
    /**
     * Вычисляю выражение
     * @return
     */
    public static Optional<List<String>> calculate(Optional<String> expression) {
        return expression
                .filter(expr -> !expr.isEmpty()) // передаю далее по цепочке НЕпустую строку
                .map(FlatMapCalc::removeSpaces) // удаляю пробелы
                .filter(expr -> !expr.isEmpty()) // если строка была из пробелов
                .flatMap(expr -> FlatMapCalc.getTokens(Optional.of(expr))); // преобразую в список лексем - FlatMapCalc::getTokens не работает, разбираюсь
        // здесь вычисление
    }
    

    public static void main(String[] args) {
        String exp1 = "    1   +  2  ";
        
        // проверка удаления пробелов
        String exp1NoSpaces = removeSpaces(exp1);
        System.out.println(exp1NoSpaces); // 1+2
        
        // вычисление
        // todo
        System.out.println(getTokens(Optional.of(exp1NoSpaces))); // Optional[[1, +, 2]]
        
    }
}
