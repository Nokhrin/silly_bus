package account.operations.amount;

import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.assertEquals;

public class TransactionAmountTypeSafetyTest {
// todo    
//    @Test(expectedExceptions = IllegalArgumentException.class)
//    public void testTransactionAmountRejectsZeroValue() {
//        new TransactionAmount(BigDecimal.ZERO);
//    }
//
//    @Test(expectedExceptions = IllegalArgumentException.class)
//    public void testTransactionAmountRejectsNegativeValue() {
//        new TransactionAmount(new BigDecimal("-10.50"));
//    }
//
//    @Test(expectedExceptions = IllegalArgumentException.class)
//    public void testTransactionAmountRejectsNullValue() {
//        new TransactionAmount((BigDecimal) null);
//    }

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