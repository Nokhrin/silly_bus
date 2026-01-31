package account.system;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Хранение данных - интерфейс.
 */
public sealed interface AccountRepository permits InMemoryAccountRepository {
    void saveAccount(Account account);
    Optional<Account> loadAccount(UUID accountId);
    void deleteAccount(UUID accountId);
    List<Account> loadExistingAccounts();
}
