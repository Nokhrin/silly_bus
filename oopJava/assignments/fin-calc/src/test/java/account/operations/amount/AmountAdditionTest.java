package account.operations.amount;

import org.testng.annotations.Test;
import org.testng.Assert;
import java.math.BigDecimal;

public class AmountAdditionTest {

    @Test(description = "Сохранение типа в результате сложения")
    public void testAmountAdditionBehavior() {
        // TransactionAmount + BalanceAmount = TransactionAmount
        Amount transaction = new TransactionAmount("100.00");
        Amount balance = new BalanceAmount("50.00");

        Amount result = transaction.add(balance);
        Assert.assertTrue(result instanceof TransactionAmount);
        Assert.assertEquals(result.getValue(), new BigDecimal("150.00"));

        // BalanceAmount + TransactionAmount = BalanceAmount
        Amount result2 = balance.add(transaction);
        Assert.assertTrue(result2 instanceof BalanceAmount);
        Assert.assertEquals(result2.getValue(), new BigDecimal("150.00"));

        // BalanceAmount + BalanceAmount = BalanceAmount
        Amount balance2 = new BalanceAmount("30.00");
        Amount result3 = balance.add(balance2);
        Assert.assertTrue(result3 instanceof BalanceAmount);
        Assert.assertEquals(result3.getValue(), new BigDecimal("80.00"));
    }

    @Test(description = "Сохранение типа в результате вычитания")
    public void testAmountSubtractionBehavior() {
        // TransactionAmount - BalanceAmount = TransactionAmount
        Amount transaction = new TransactionAmount("100.00");
        Amount balance = new BalanceAmount("50.00");

        Amount result = transaction.sub(balance);
        Assert.assertTrue(result instanceof TransactionAmount);
        Assert.assertEquals(result.getValue(), new BigDecimal("50.00"));

        // BalanceAmount - TransactionAmount = BalanceAmount
        Amount result2 = balance.sub(transaction);
        Assert.assertTrue(result2 instanceof BalanceAmount);
        Assert.assertEquals(result2.getValue(), new BigDecimal("-50.00"));
    }
}