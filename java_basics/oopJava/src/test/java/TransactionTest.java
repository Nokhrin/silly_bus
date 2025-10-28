import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigInteger;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class TransactionTest {

    private static final Set<String> VALID_CURRENCIES = Set.of("USD", "EUR", "RUB");

    @Test(groups = "valid", description = "Должен создать валидную транзакцию")
    public void shouldCreateValidTransaction() {
        Transaction transaction = new Transaction("TX123", BigInteger.valueOf(1000), "USD");

        assertEquals(transaction.getId(), "TX123");
        assertEquals(transaction.getAmount(), BigInteger.valueOf(1000));
        assertEquals(transaction.getCurrency(), "USD");
    }

    @DataProvider(name = "validTransactions")
    public Object[][] validTransactions() {
        return new Object[][]{
                {"TX1", 1000, "USD"},
                {"TX2", 500, "EUR"},
                {"TX3", 15000, "RUB"}
        };
    }

    @Test(groups = "valid", dataProvider = "validTransactions", description = "Должен создать транзакцию с разными валидными значениями")
    public void shouldCreateTransactionWithValidData(String id, long amount, String currency) {
        Transaction transaction = new Transaction(id, BigInteger.valueOf(amount), currency);

        assertEquals(transaction.getId(), id);
        assertEquals(transaction.getAmount(), BigInteger.valueOf(amount));
        assertEquals(transaction.getCurrency(), currency);
    }

    @Test(groups = "invalid", expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = ".*null.*")
    @DataProvider(name = "nullId")
    public Object[][] nullId() {
        return new Object[][]{
                {null, BigInteger.ONE, "USD"}
        };
    }

    @Test(groups = "invalid", dataProvider = "nullId", expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowWhenIdIsNull(String id, BigInteger amount, String currency) {
        new Transaction(id, amount, currency);
    }

    @Test(groups = "invalid", expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = ".*отрицательное.*")
    public void shouldThrowWhenAmountIsNegative() {
        new Transaction("TX1", BigInteger.valueOf(-100), "USD");
    }

    @Test(groups = "invalid", expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = ".*недопустимое.*")
    public void shouldThrowWhenCurrencyIsInvalid() {
        new Transaction("TX1", BigInteger.ONE, "XYZ");
    }

    @Test(groups = "invalid", expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = ".*недопустимое.*")
    public void shouldRejectEmptyCurrency() {
        new Transaction("TX1", BigInteger.ONE, "");
    }

    @Test(groups = "invalid", expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = ".*недопустимое.*")
    public void shouldRejectWhitespaceCurrency() {
        new Transaction("TX1", BigInteger.ONE, "  ");
    }

    @Test(groups = "invalid", expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = ".*недопустимое.*")
    public void shouldRejectCaseVariants() {
        new Transaction("TX1", BigInteger.ONE, "usd");
    }
}