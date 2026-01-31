package account.system;

import account.operations.amount.Amount;
import account.operations.amount.PositiveAmount;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.*;

/**
 * Тесты Amount.
 * Показывают 
 *  валидацию величины значения Amount
 *  невозможность создания значения Amount непредусмотренного типа
 */
public class AmountTest {

    //region Нормальные позитивные случаи
    @Test(description = "Валидная сумма с копейками")
    public void testPositiveAmountCreation() {
        Amount amount = Amount.of("100.50");
        assertEquals(amount.getValue(), new BigDecimal("100.50"));
    }

    @Test(description = "Граничное - минимальная валидная сумма")
    public void testMinimalPositiveAmount() {
        Amount amount = Amount.of("0.01");
        assertEquals(amount.getValue(), new BigDecimal("0.01"));
    }

    @Test
    public void testIntegerAmount() {
        Amount amount = Amount.of("100");
        assertEquals(amount.getValue().compareTo(new BigDecimal("100")), 0);
    }
    //endregion
    
    
    //region Ошибочные случаи
    @Test(description = "Отрицательная сумма",
            expectedExceptions = IllegalArgumentException.class)
    public void testNegativeAmount() {
        Amount.of("-100.50");
    }

    @Test(description = "Нулевая сумма",
            expectedExceptions = IllegalArgumentException.class)
    public void testZeroAmount() {
        Amount.of("0");
    }

//    @Test(description = "Сумма = null",
//            expectedExceptions = IllegalArgumentException.class)
//    public void testNullAmount() {
//        Amount.of(null);
//    }

    @Test(description = "Сумма - нечисловая строка",
            expectedExceptions = IllegalArgumentException.class)
    public void testInvalidStringAmount() {
        Amount.of("abc");
    }
    //endregion
    
    
    //region Пограничные условия значений
    @Test(description = "Нули слева игнорируются")
    public void testAmountWithLeadingZeros() {
        Amount amount = Amount.of("000100.500");
        assertEquals(amount.getValue(), new BigDecimal("100.50"));
    }

    @Test(description = "Нули справа игнорируются")
    public void testAmountWithTrailingZeros() {
        Amount amount = Amount.of("100.5000");
        assertEquals(amount.getValue(), new BigDecimal("100.50"));
    }
    //endregion
 
 
    //region Краевые логические ошибки
    @Test(description = "Валидное сложение")
    public void testAmountAddition() {
        Amount amount1 = Amount.of("100.50");
        Amount amount2 = Amount.of("50.25");
        Amount result = amount1.add(amount2);
        assertEquals(result.getValue(), new BigDecimal("150.75"));
    }

    @Test(description = "Валидное вычитание")
    public void testAmountSubtraction() {
        Amount amount1 = Amount.of("100.50");
        Amount amount2 = Amount.of("50.25");
        Amount result = amount1.sub(amount2);
        assertEquals(result.getValue(), new BigDecimal("50.25"));
    }

    @Test(description = "Вычитание, возвращающее сумму = 0", 
            expectedExceptions = IllegalArgumentException.class)
    public void testSubtractionResultingInZero() {
        Amount amount1 = Amount.of("100.50");
        Amount amount2 = Amount.of("100.50");
        Amount result = amount1.sub(amount2);
    }
    //endregion
 
    //region Типобезопасность - создание с корректным типом
    @Test(description = "Тип значения суммы соответствует ожидаемому")
    public void testTypeSafety() {
        Amount amount = Amount.of("100.50");
        assertTrue(amount instanceof PositiveAmount);

        // Невозможно создать объект другого типа
        // Компилятор не позволит создать экземпляр вне permits
    }
    //endregion
}