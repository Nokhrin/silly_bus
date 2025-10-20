import org.testng.Assert;
import org.testng.annotations.Test;
import java.math.BigInteger;
import java.util.Set;

public class TransactionTest {

    private static final Set<String> CURRENCIES_AVAILABLE = Set.of("USD", "EUR", "RUB");

    // Group: isValid
    @Test(groups = "isValid")
    public void testIsValidReturnsTrueForValidTransaction() {
        Transaction tx = new Transaction("tx-1", BigInteger.valueOf(100), "EUR");
        Assert.assertTrue(Transaction.isValid(tx), "isValid should return true for valid transaction");
    }

    @Test(groups = "isValid")
    public void testIsValidReturnsFalseForNullTransaction() {
        Assert.assertFalse(Transaction.isValid(null), "isValid should return false for null transaction");
    }

    @Test(groups = "isValid")
    public void testIsValidReturnsFalseForNullAmount() {
        Transaction tx = new Transaction("tx-2", null, "USD");
        Assert.assertFalse(Transaction.isValid(tx), "isValid should return false when amount is null");
    }

    @Test(groups = "isValid")
    public void testIsValidReturnsFalseForNegativeAmount() {
        Transaction tx = new Transaction("tx-3", BigInteger.valueOf(-10), "USD");
        Assert.assertFalse(Transaction.isValid(tx), "isValid should return false when amount is negative");
    }

    @Test(groups = "isValid")
    public void testIsValidReturnsFalseForNullCurrency() {
        Transaction tx = new Transaction("tx-4", BigInteger.ONE, null);
        Assert.assertFalse(Transaction.isValid(tx), "isValid should return false when currency is null");
    }

    @Test(groups = "isValid")
    public void testIsValidReturnsFalseForInvalidCurrency() {
        Transaction tx = new Transaction("tx-5", BigInteger.valueOf(100), "JPY");
        Assert.assertFalse(Transaction.isValid(tx), "isValid should return false for invalid currency");
    }

    @Test(groups = "isValid")
    public void testIsValidReturnsTrueForZeroAmount() {
        Transaction tx = new Transaction("tx-6", BigInteger.ZERO, "USD");
        Assert.assertTrue(Transaction.isValid(tx), "isValid should return true for zero amount");
    }

    @Test(groups = "isValid")
    public void testIsValidReturnsTrueForValidCurrency() {
        Transaction tx = new Transaction("tx-7", BigInteger.valueOf(50), "RUB");
        Assert.assertTrue(Transaction.isValid(tx), "isValid should return true for valid currency");
    }

    // Group: constructor
    @Test(groups = "constructor", expectedExceptions = IllegalArgumentException.class)
    public void testConstructorThrowsExceptionForNullId() {
        new Transaction(null, BigInteger.ONE, "USD");
    }

    @Test(groups = "constructor", expectedExceptions = IllegalArgumentException.class)
    public void testConstructorThrowsExceptionForNullAmount() {
        new Transaction("tx-8", null, "USD");
    }

    @Test(groups = "constructor", expectedExceptions = IllegalArgumentException.class)
    public void testConstructorThrowsExceptionForNegativeAmount() {
        new Transaction("tx-9", BigInteger.valueOf(-10), "USD");
    }

    @Test(groups = "constructor", expectedExceptions = IllegalArgumentException.class)
    public void testConstructorThrowsExceptionForNullCurrency() {
        new Transaction("tx-10", BigInteger.ONE, null);
    }

    @Test(groups = "constructor", expectedExceptions = IllegalArgumentException.class)
    public void testConstructorThrowsExceptionForInvalidCurrency() {
        new Transaction("tx-11", BigInteger.valueOf(100), "JPY");
    }

    @Test(groups = "constructor")
    public void testConstructorCreatesValidTransaction() {
        Transaction tx = new Transaction("tx-12", BigInteger.valueOf(100), "USD");
        Assert.assertNotNull(tx, "Transaction should not be null");
        Assert.assertEquals(tx.getAmount(), BigInteger.valueOf(100), "Amount should be 100");
        Assert.assertEquals(tx.getCurrency(), "USD", "Currency should be USD");
        Assert.assertEquals(tx.getId(), "tx-12", "ID should be tx-12");
    }

    // Group: getters
    @Test(groups = "getters")
    public void testGettersReturnCorrectValues() {
        Transaction tx = new Transaction("tx-13", BigInteger.valueOf(100), "EUR");
        Assert.assertEquals(tx.getId(), "tx-13");
        Assert.assertEquals(tx.getAmount(), BigInteger.valueOf(100));
        Assert.assertEquals(tx.getCurrency(), "EUR");
    }

    // Group: equals and hashCode
    @Test(groups = "equals-hashCode")
    public void testEqualsAndHashCodeWithSameId() {
        Transaction tx1 = new Transaction("tx-14", BigInteger.valueOf(100), "USD");
        Transaction tx2 = new Transaction("tx-14", BigInteger.valueOf(100), "USD");
        Assert.assertEquals(tx1, tx2, "Transactions with same ID should be equal");
        Assert.assertEquals(tx1.hashCode(), tx2.hashCode(), "Hash codes should be equal");
    }

    @Test(groups = "equals-hashCode")
    public void testEqualsAndHashCodeWithDifferentId() {
        Transaction tx1 = new Transaction("tx-15", BigInteger.valueOf(100), "USD");
        Transaction tx2 = new Transaction("tx-16", BigInteger.valueOf(100), "USD");
        Assert.assertNotEquals(tx1, tx2, "Transactions with different IDs should not be equal");
        Assert.assertNotEquals(tx1.hashCode(), tx2.hashCode(), "Hash codes should differ");
    }

    // Group: id
    @Test(groups = "id")
    public void testIdIsImmutable() {
        Transaction tx = new Transaction("tx-17", BigInteger.valueOf(100), "USD");
        Assert.assertEquals(tx.getId(), "tx-17", "ID should be immutable and correctly set");
    }

    // Group: integration
    @Test(groups = "integration")
    public void testIntegrationIsValidAndConstructor() {
        Transaction tx = new Transaction("tx-18", BigInteger.valueOf(100), "EUR");
        Assert.assertTrue(Transaction.isValid(tx), "isValid should return true for valid transaction");
    }
}