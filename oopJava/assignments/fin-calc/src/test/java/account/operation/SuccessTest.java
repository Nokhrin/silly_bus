package account.operation;

import account.system.Account;
import account.system.Amount;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.testng.Assert.*;

public class SuccessTest {

    @Test(description = "Тест успешного результата операции")
    public void testSuccessResult() {
        Account account = new Account(new Amount(BigDecimal.valueOf(1000)));
        OperationResult successResult = new Success(Optional.of(account));

        assertTrue(successResult.isSuccess());
        assertTrue(successResult.account().isPresent());
        assertEquals(successResult.account().get(), account);
    }

}