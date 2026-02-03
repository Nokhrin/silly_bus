package account.operations.result;

import account.operations.amount.Amount;
import account.operations.amount.TransactionAmount;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.testng.Assert.*;

public class OperationResultTest {
    @Test(description = "Проверка полиморфизма и типобезопасности")
    public void testPolymorphismAndTypeSafetyWithPatternMatching() {
        SoftAssert softAssert = new SoftAssert();

        SuccessResult<String> successResult = new SuccessResult<>(
                "test_value",
                "TestOperation",
                UUID.randomUUID(),
                LocalDateTime.now(),
                "Success message",
                true
        );

        FailureResult failureResult = new FailureResult(
                "TestOperation",
                UUID.randomUUID(),
                LocalDateTime.now(),
                "Failure message",
                false
        );

        // SuccessResult и FailureResult реализуют интерфейс OperationResult
        
        OperationResult result = successResult;  // статическая типизация ссылки result
        String value = switch (result) {
            case SuccessResult<?> s when s.isSuccess() -> (String) s.value();  // динамическая типизация SuccessResult
            default -> "default";
        };
        assertEquals(value, "test_value");

        result = failureResult;
        String message = switch (result) {
            case FailureResult f when !f.isSuccess() -> f.message();
            default -> "default";
        };
        assertEquals(message, "Failure message");

        // полиморфизм - разные подтипы реализуют методы с одной сигнатурой 
        softAssert.assertTrue(successResult.isSuccess());
        softAssert.assertFalse(failureResult.isSuccess());
        softAssert.assertTrue(successResult.isStateModified());
        softAssert.assertFalse(failureResult.isStateModified());

        softAssert.assertAll();
    }

    @Test(description = "Позитивы: SuccessResult + value разных типов")
    public void testNormalPositiveCases() {
        SoftAssert softAssert = new SoftAssert();

        UUID operationId = UUID.randomUUID();
        LocalDateTime timestamp = LocalDateTime.now();

        SuccessResult<String> stringResult = new SuccessResult<>(
                "test_string",
                "TestOperation",
                operationId,
                timestamp,
                "String обработан",
                false
        );

        assertEquals(stringResult.value(), "test_string");
        assertEquals(stringResult.operationName(), "TestOperation");
        assertEquals(stringResult.operationId(), operationId);
        assertEquals(stringResult.operationTimestamp(), timestamp);
        assertEquals(stringResult.message(), "String обработан");
        assertTrue(stringResult.isSuccess());
        assertFalse(stringResult.isStateModified());

        Amount amount = new TransactionAmount("100.50");
        SuccessResult<Amount> decimalResult = new SuccessResult<>(
                amount,
                "TestOperation",
                operationId,
                timestamp,
                "TransactionAmount обработан",
                true
        );

        assertEquals(decimalResult.value(), amount);
        assertEquals(decimalResult.message(), "TransactionAmount обработан");
        assertTrue(decimalResult.isStateModified());

        softAssert.assertAll();
    }
}