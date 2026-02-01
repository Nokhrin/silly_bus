package account.system;

import account.operations.ListAccounts;

import java.util.*;

/**
 * Хранение в памяти.
 */
public final class InMemoryAccountRepository implements AccountRepository {
    private final Map<UUID, Account> accountMap = new HashMap<>();

    @Override
    public RepositoryResult<Account> saveAccount(Account account) {
        String description = "Записать в хранилище счет: " + account.id();
        accountMap.put(account.id(), account);
        return new RepositoryResult.Success<>(account, true, description);
    }

    @Override
    public RepositoryResult<Void> deleteAccount(UUID accountId) {
        String description = "Удалить из хранилища счет: " + accountId;
        accountMap.remove(accountId);
        return new RepositoryResult.Success<>(null, true, description);
    }

    @Override
    public RepositoryResult<Account> loadAccount(UUID accountId) {
        String description = "Прочитать из хранилища счет: " + accountId;
        Account account = accountMap.get(accountId);
        
        if (account == null) {
            return new RepositoryResult.Failure<>(null, false, description);
        }

        return new RepositoryResult.Success<>(account, false, description);
    }

    @Override
    public RepositoryResult<List<Account>> loadExistingAccounts() {
        String description = "Прочитать счета, существующие в хранилище";
        List<Account> accountList = accountMap.values().stream().toList();
        return new RepositoryResult.Success<>(accountList, false, description);
    }

}
