package account.operations.amount;

import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

public class TransactionAmountTypeSafetyTest {
    @Test(description = "Типобезопасность: TransactionAmount не может быть создан с нулевым значением")
    public void testTransactionAmountRejectsZeroValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            TransactionAmount.of(BigDecimal.ZERO);
    });
    }

    @Test(description = "Типобезопасность: TransactionAmount не может быть создан с отрицательным значением")
    public void testTransactionAmountRejectsNegativeValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            TransactionAmount.of(new BigDecimal("-12.34"));
        });
    }

    @Test(description = "Типобезопасность: TransactionAmount не может быть создан значением null")
    public void testTransactionAmountRejectsNullValue() {
        assertThrows(IllegalArgumentException.class, () ->{
            new TransactionAmount(null);
        });
    }

    @Test
    public void testTransactionAmountAcceptsValidPositiveValue() {
        TransactionAmount valid = new TransactionAmount("100.00");
        assertEquals(valid.getValue(), new BigDecimal("100.00"));
    }

    @Test(description = "Округление корректно")
    public void testTransactionAmountCreationFromString() {
        TransactionAmount amount = new TransactionAmount("123.45");
        assertEquals(amount.getValue(), new BigDecimal("123.45"));

        TransactionAmount amount2 = new TransactionAmount("123.450000");
        assertEquals(amount2.getValue(), new BigDecimal("123.45"));
    }
}