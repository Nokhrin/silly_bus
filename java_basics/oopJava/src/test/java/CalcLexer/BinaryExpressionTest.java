package CalcLexer;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Test
public class BinaryExpressionTest {
    //region TestName: One Number Expression
    @Test(groups = {"expression"}, description = "Проверка вычисления одного числа: 1")
    public void testOneNumber() {
        Expression expr = new BinaryExpression(new NumValue(1.0), Parsers.Operation.ADD, new NumValue(0.0));
        // Используем ADD, чтобы использовать структуру, но результат должен быть 1.0
        // Альтернативно можно было бы сделать прямой вызов, но структура BinaryExpression позволяет только бинарные операции
        // Поэтому используем 1 + 0
        assertEquals(expr.evaluate(), 1.0, "Ожидается 1.0 для выражения 1");
    }

    @Test(groups = {"expression"}, description = "Проверка вычисления одного числа: 42")
    public void testOneNumberWith42() {
        Expression expr = new BinaryExpression(new NumValue(42.0), Parsers.Operation.ADD, new NumValue(0.0));
        assertEquals(expr.evaluate(), 42.0, "Ожидается 42.0 для выражения 42");
    }
    //endregion

    // Тесты для 1+2
    //region TestName: Simple Addition 1+2
    @Test(groups = {"expression", "binOp"}, description = "Проверка выражения 1+2 -> 3")
    public void testSimpleAddition() {
        Expression expr = new BinaryExpression(
                new NumValue(1.0),
                Parsers.Operation.ADD,
                new NumValue(2.0)
        );
        assertEquals(expr.evaluate(), 3.0, "Ожидается 3.0 для 1+2");
    }

    @Test(groups = {"expression", "binOp"}, description = "Проверка выражения 1+2+3 -> 6 (последовательно)")
    public void testAdditionChain() {
        // 1+2+3 = (1+2)+3 = 6
        Expression inner = new BinaryExpression(
                new NumValue(1.0),
                Parsers.Operation.ADD,
                new NumValue(2.0)
        );
        Expression expr = new BinaryExpression(
                inner,
                Parsers.Operation.ADD,
                new NumValue(3.0)
        );
        assertEquals(expr.evaluate(), 6.0, "Ожидается 6.0 для 1+2+3");
    }

    @Test(groups = {"expression", "binOp"}, description = "Проверка выражения 1+2+3-4 -> 2")
    public void testAdditionSubtraction() {
        // 1+2+3-4 = ((1+2)+3)-4 = 6-4 = 2
        Expression expr = new BinaryExpression(
                new BinaryExpression(
                        new BinaryExpression(
                                new NumValue(1.0),
                                Parsers.Operation.ADD,
                                new NumValue(2.0)
                        ),
                        Parsers.Operation.ADD,
                        new NumValue(3.0)
                ),
                Parsers.Operation.SUB,
                new NumValue(4.0)
        );
        assertEquals(expr.evaluate(), 2.0, "Ожидается 2.0 для 1+2+3-4");
    }
}