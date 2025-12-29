package account.system;

import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.testng.Assert.*;

public class AccountRegistryTest {
    private final AccountRegistry registry = new AccountRegistry();

    //region openAccount

    @Test(description = "Успешное открытие нового счёта (счета не было)")
    public void testOpenAccount_Success_NewAccount() {
        UUID id = UUID.randomUUID();

        boolean result = registry.openAccount(id);

        assertTrue(result);
        assertEquals(registry.getBalance(id), BigDecimal.ZERO);
    }

    @Test(description = "Попытка открытия существующего счёта — возврат false")
    public void testOpenAccount_Failure_ExistingAccount() {
        UUID id = UUID.randomUUID();
        registry.openAccount(id);

        boolean result = registry.openAccount(id);

        assertFalse(result);
        assertEquals(registry.getBalance(id), BigDecimal.ZERO);
    }

    //endregion

    //region closeAccount

    @Test(description = "Успешное закрытие существующего счёта")
    public void testCloseAccount_Success_ExistingAccount() {
        UUID id = UUID.randomUUID();
        registry.openAccount(id);

        boolean result = registry.closeAccount(id);

        assertTrue(result);
        assertNull(registry.getBalance(id));
    }

    @Test(description = "Попытка закрытия несуществующего счёта — возврат false")
    public void testCloseAccount_Failure_NonExistingAccount() {
        UUID id = UUID.randomUUID();

        boolean result = registry.closeAccount(id);

        assertFalse(result);
    }

    //endregion

    //region deposit

    @Test(description = "Успешное зачисление на существующий счёт")
    public void testDeposit_Success_ExistingAccount() {
        UUID id = UUID.randomUUID();
        registry.openAccount(id);

        boolean result = registry.deposit(id, new BigDecimal("100.50"));

        assertTrue(result);
        assertEquals(registry.getBalance(id), new BigDecimal("100.50"));
    }

    @Test(description = "Попытка зачисления на несуществующий счёт — возврат false")
    public void testDeposit_Failure_NonExistingAccount() {
        UUID id = UUID.randomUUID();

        boolean result = registry.deposit(id, new BigDecimal("100.00"));

        assertFalse(result);
    }

    //endregion

    //region withdraw

    @Test(description = "Успешное снятие со счёта с достаточными средствами")
    public void testWithdraw_Success_SufficientFunds() {
        UUID id = UUID.randomUUID();
        registry.openAccount(id);
        registry.deposit(id, new BigDecimal("200.00"));

        boolean result = registry.withdraw(id, new BigDecimal("50.00"));

        assertTrue(result);
        assertEquals(registry.getBalance(id), new BigDecimal("150.00"));
    }

    @Test(description = "Попытка снятия со счёта с недостаточными средствами — возврат false")
    public void testWithdraw_Failure_InsufficientFunds() {
        UUID id = UUID.randomUUID();
        registry.openAccount(id);
        registry.deposit(id, new BigDecimal("50.00"));

        boolean result = registry.withdraw(id, new BigDecimal("100.00"));

        assertFalse(result);
        assertEquals(registry.getBalance(id), new BigDecimal("50.00"));
    }

    //endregion

    //region transfer

    @Test(description = "Успешный перевод со счёта на счёт")
    public void testTransfer_Success_SourceHasFunds() {
        UUID sourceId = UUID.randomUUID();
        UUID targetId = UUID.randomUUID();

    }
}