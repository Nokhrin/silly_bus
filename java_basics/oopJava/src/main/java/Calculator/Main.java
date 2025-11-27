package Calculator;

/**
 * Надо набор функций, парсеров
 *
 * === 1 ===
 * Парсинг числа
 * 
 * === 2 ===
 * Пробелов 1 или больше (1+)
 * 
 * === 3 ===
 * Парсинг операторов сложения или вычитания
 *
 * Должно быть три+ функции
 *
 * Функции описываються как я описал
 * Принимаетют
 * Исходник и позицию в нем
 *
 * Возвращают опционально, если совпало
 * Значение
 * Следующую позицию в исходнике за значением
 * 
 * 
 * Напиши к этим трем функциям тесты
 * 
 * Optional<Result<Integer>>
 *     Result - класс-контейнер, содержит пару value:offset - значение и финальное смещение
 */
public class Main {
    public static void main(String[] args) {
        
        System.out.println("record demo");
        ParseResult<Integer> result = new ParseResult<>(42, 5, 6);
        System.out.println(result.value());   // 42
        System.out.println(result.start());   // 5
        System.out.println(result.end());     // 6
    }
}
