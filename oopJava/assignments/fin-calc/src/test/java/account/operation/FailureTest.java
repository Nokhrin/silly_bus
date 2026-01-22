package account.operation;

import account.system.Account;
import account.system.Amount;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class FailureTest {
    @Test(description = "Тест неудачи выполнения операции")
    public void testSuccessResult() {
        Account account = new Account(new Amount(BigDecimal.valueOf(1000)));

        OperationResult successResult = new Success(Optional.of(account));
        OperationResult failureResult = new Failure("Недостаточно средств");

        // Проверяем типы объектов
        assertTrue(successResult instanceof Success);
        assertFalse(successResult instanceof Failure);

        assertFalse(failureResult instanceof Success);
        assertTrue(failureResult instanceof Failure);
    }
}