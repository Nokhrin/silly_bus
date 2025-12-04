package CalcLexer;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Ход мысли при разработке тестов
 * 
 * экземпляр record имеет свойства:
 *  неизменяемо - это гарантируется Java, не проверяю
 *  генерирует конструктор, геттеры, методы equals, hashCode, toString
 *  может переопределять методы
 *  реализует интерфейсы
 *      - работает с implements
 *      - может реализовывать sealed интерфейс
 *      
 * Что тестируем
 *  ParseResult<T> корректно создается
 *  equals, hashCode, toString - сгенерированы, работают
 *  value, start, end - геттеры - сгенерированы, работают
 *  подстановка типа - ParseResult<Number> - это ParseResult<Integer>
 */
public class ParseResultTest {

    // Проверка геттеров 1
    @Test
    public void testGetters1() {
        ParseResult<Integer> result = new ParseResult<>(42, 0, 3);

        Assert.assertEquals(result.value(), 42);
        Assert.assertEquals(result.start(), 0);
        Assert.assertEquals(result.end(), 3);
    }

    // Проверка геттеров 2
    @Test
    public void testGetters2() {
        ParseResult<String> result = new ParseResult<>("+", 1, 2);

        Assert.assertEquals(result.value(), "+");
        Assert.assertEquals(result.start(), 1);
        Assert.assertEquals(result.end(), 2);
    }

    // equals, hashCode, toString - работают
    @Test
    public void testEqualsAndHashCodeToString() {
        ParseResult<Integer> r1 = new ParseResult<>(42, 0, 3);
        ParseResult<Integer> r2 = new ParseResult<>(42, 0, 3);
        ParseResult<Integer> r3 = new ParseResult<>(43, 0, 3);
        ParseResult<Integer> r4 = new ParseResult<>(42, 0, 4);

        // Проверка равенства
        Assert.assertEquals(r1, r2);
        Assert.assertNotEquals(r1, r3);
        Assert.assertNotEquals(r1, r4);

        // Проверка хэш-кода
        Assert.assertEquals(r1.hashCode(), r2.hashCode());
        Assert.assertNotEquals(r1.hashCode(), r3.hashCode());

        // Проверка toString
        String expected = "ParseResult[value=42, start=0, end=3]";
        Assert.assertEquals(r1.toString(), expected);
    }

    // Переопределение toString
    @Test
    public void testOverrideToString() {
        ParseResult<Integer> result = new ParseResult<>(42, 0, 3);

        // record автоматически генерирует toString - можно проверить
        String actual = result.toString();
        Assert.assertTrue(actual.startsWith("ParseResult[value="));
        Assert.assertTrue(actual.contains("start=0"));
        Assert.assertTrue(actual.contains("end=3"));
    }
}