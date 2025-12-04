package CalcParser;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * На момент написания тестов есть два класса, реализующих интерфейс Expression
 *  - NumValue
 *  - BinOp
 * 
 * Задача - изолировать тесты по признаку класса (NumValue и BinOp в примере),
 *  чтобы иметь возможность выполнить тест наследника, не создавая отдельный класс для тестов
 *  
 * Использую аннотацию @Test(groups = ) - для разделения на уровне выполнения
 * description - для цели выполняемой проверки
 * 
 * Использую region - для визуальной группировки в IDEA
 *  
 */
public class ExpressionTest {

    //region NumValue Tests
    @Test(groups = "numValue", description = "Проверка вычисления значения числа")
    public void testNumValueEvaluate() {
        Expression expr = new NumValue(42.0);
        assertEquals(expr.evaluate(), 42.0);
    }

    @Test(groups = "numValue", description = "Проверка отрицательного числа")
    public void testNumValueEvaluateNegative() {
        Expression expr = new NumValue(-10.5);
        assertEquals(expr.evaluate(), -10.5);
    }

    @Test(groups = "numValue", description = "Проверка нулевого значения")
    public void testNumValueEvaluateZero() {
        Expression expr = new NumValue(0.0);
        assertEquals(expr.evaluate(), 0.0);
    }
    //endregion NumValue

    //region BinOp Tests
    @Test(groups = "binOp", description = "Сложение: 1 + 2 = 3")
    public void testBinOpAdd() {
        Expression expr = new BinOp(1, Parser.Operation.ADD, 2);
        assertEquals(expr.evaluate(), 3.0);
    }

    @Test(groups = "binOp", description = "Вычитание: 5 - 3 = 2")
    public void testBinOpSub() {
        Expression expr = new BinOp(5, Parser.Operation.SUB, 3);
        assertEquals(expr.evaluate(), 2.0);
    }

    @Test(groups = "binOp", description = "Умножение: 3 * 4 = 12")
    public void testBinOpMul() {
        Expression expr = new BinOp(3, Parser.Operation.MUL, 4);
        assertEquals(expr.evaluate(), 12.0);
    }

    @Test(groups = "binOp", description = "Деление: 10 / 2 = 5")
    public void testBinOpDiv() {
        Expression expr = new BinOp(10, Parser.Operation.DIV, 2);
        assertEquals(expr.evaluate(), 5.0);
    }

    @Test(
            groups = "binOp",
            expectedExceptions = ArithmeticException.class,
            description = "Проверка деления на ноль"
    )
    public void testDivideByZero() {
        Expression expr = new BinOp(1, Parser.Operation.DIV, 0);
        expr.evaluate(); // Должно выбросить ArithmeticException
    }
    //endregion BinOp
}