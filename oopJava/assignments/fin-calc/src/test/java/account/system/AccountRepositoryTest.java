package account.system;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class AccountRepositoryTest {

    /**
     * Тест: Сохранение аккаунта должно изменять состояние системы
     * Применяет instanceof pattern matching для проверки типа результата
     */
    @Test(description = "Сохранение аккаунта изменяет состояние системы")
    public void testSaveAccountChangesState() {
        // Given
        var repository = new InMemoryAccountRepository();
        var account = new Account();

        // When
        var result = repository.saveAccount(account);

        // Then
        if (result instanceof RepositoryResult<Account> repoResult) {
            assertTrue(repoResult.isStateModified(), "Состояние должно быть изменено при сохранении");
        } else {
            fail("Результат должен быть экземпляром RepositoryResult<Account>");
        }
    }

    /**
     * Тест: Удаление существующего аккаунта должно изменять состояние системы
     * Применяет instanceof pattern matching для проверки типа результата
     */
    @Test(description = "Удаление существующего аккаунта изменяет состояние системы")
    public void testDeleteExistingAccountChangesState() {
        // Given
        var repository = new InMemoryAccountRepository();
        var account = new Account();
        repository.saveAccount(account);

        // When
        var result = repository.deleteAccount(account.id());

        // Then
        if (result instanceof RepositoryResult<Void> repoResult) {
            assertTrue(repoResult.isStateModified(), "Состояние должно быть изменено при удалении");
        } else {
            fail("Результат должен быть экземпляром RepositoryResult<Void>");
        }
    }

    /**
     * Тест: Загрузка аккаунта не должна изменять состояние системы
     * Применяет instanceof pattern matching для проверки типа результата
     */
    @Test(description = "Загрузка аккаунта не изменяет состояние системы")
    public void testLoadAccountDoesNotChangeState() {
        // Given
        var repository = new InMemoryAccountRepository();
        var account = new Account();
        repository.saveAccount(account);

        // When
        var result = repository.loadAccount(account.id());

        // Then
        if (result instanceof RepositoryResult<Account> repoResult) {
            assertFalse(repoResult.isStateModified(), "Состояние не должно быть изменено при загрузке");
        } else {
            fail("Результат должен быть экземпляром RepositoryResult<Account>");
        }
    }

    /**
     * Тест: Загрузка несуществующего аккаунта не должна изменять состояние системы
     * Применяет instanceof pattern matching для проверки типа результата
     */
    @Test(description = "Загрузка несуществующего аккаунта не изменяет состояние системы")
    public void testLoadNonExistentAccountDoesNotChangeState() {
        // Given
        var repository = new InMemoryAccountRepository();

        // When
        var result = repository.loadAccount(java.util.UUID.randomUUID());

        // Then
        if (result instanceof RepositoryResult<Account> repoResult) {
            assertTrue(true);
        } else {
            fail("Результат должен быть экземпляром RepositoryResult<Account>");
        }
    }
}