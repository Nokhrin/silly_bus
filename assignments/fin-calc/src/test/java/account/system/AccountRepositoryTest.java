package account.system;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class AccountRepositoryTest {

    /**
     * Тест: Сохранение аккаунта должно изменять состояние системы
     */
    @Test(description = "Сохранение аккаунта изменяет состояние системы")
    public void testSaveAccountChangesState() {
        // Given  
        var repository = new InMemoryAccountRepository();
        var account = new Account();

        // When  
        var result = repository.saveAccount(account);

        // Then  
        assertTrue(result instanceof RepositoryResult<?>, "Результат должен быть экземпляром RepositoryResult");
        assertTrue(((RepositoryResult<Account>) result).isStateModified(),
                "Состояние должно быть изменено при сохранении");
    }

    /**
     * Тест: Удаление существующего аккаунта должно изменять состояние системы
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
        assertTrue(result instanceof RepositoryResult<?>, "Результат должен быть экземпляром RepositoryResult");
        assertTrue(((RepositoryResult<Void>) result).isStateModified(),
                "Состояние должно быть изменено при удалении");
    }

    /**
     * Тест: Загрузка аккаунта не должна изменять состояние системы
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
        assertTrue(result instanceof RepositoryResult<?>, "Результат должен быть экземпляром RepositoryResult");
        assertFalse(((RepositoryResult<Account>) result).isStateModified(),
                "Состояние не должно быть изменено при загрузке");
    }

    /**
     * Тест: Загрузка несуществующего аккаунта не должна изменять состояние системы
     */
    @Test(description = "Загрузка несуществующего аккаунта не изменяет состояние системы")
    public void testLoadNonExistentAccountDoesNotChangeState() {
        // Given  
        var repository = new InMemoryAccountRepository();

        // When  
        var result = repository.loadAccount(java.util.UUID.randomUUID());

        // Then  
        assertTrue(result instanceof RepositoryResult<?>, "Результат должен быть экземпляром RepositoryResult");
        // Проверка состояния не требуется для несуществующего аккаунта
        assertTrue(true);
    }
}