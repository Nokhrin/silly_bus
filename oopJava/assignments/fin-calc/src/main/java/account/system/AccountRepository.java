package account.system;

import java.util.List;
import java.util.UUID;

/**
 * Хранение данных.
 */
public sealed interface AccountRepository permits InMemoryAccountRepository {
    void saveAccount(Account account);
    Account loadAccount(UUID accountId);
    void deleteAccount(UUID accountId);
    List<Account> loadExistingAccounts();
}
