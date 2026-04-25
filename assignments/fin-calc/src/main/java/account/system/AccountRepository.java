package account.system;

import java.util.List;
import java.util.UUID;

/**
 * Хранение данных - интерфейс.
 */
public sealed interface AccountRepository permits InMemoryAccountRepository {
    RepositoryResult<Account> saveAccount(Account account);
    RepositoryResult<Void> deleteAccount(UUID accountId);
    RepositoryResult<Account> loadAccount(UUID accountId);
    RepositoryResult<List<Account>> loadExistingAccounts();
}
