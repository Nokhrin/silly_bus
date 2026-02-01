package account.system;

import java.util.*;

/**
 * Хранение в памяти.
 */
public final class InMemoryAccountRepository implements AccountRepository {
    private final Map<UUID, Account> accountMap = new HashMap<>();

    @Override
    public RepositoryResult<Account> saveAccount(Account account) {
        System.out.println("\nОТЛАДКА => Записать в хранилище счет: " + account.id());
        accountMap.put(account.id(), account);
        return RepositoryResult.success(account, true);
    }

    @Override
    public RepositoryResult<Void> deleteAccount(UUID accountId) {
        boolean accountExisted = accountMap.containsKey(accountId);
        System.out.println("\nОТЛАДКА => Удалить из хранилища счет: " + accountId);
        accountMap.remove(accountId);
        return RepositoryResult.success(null, accountExisted);
    }

    @Override
    public RepositoryResult<Account> loadAccount(UUID accountId) {
        Account account = accountMap.get(accountId);
        System.out.println("\nОТЛАДКА => Прочитать из хранилища счет: " + accountId);
        return RepositoryResult.success(account, false);
    }

    @Override
    public RepositoryResult<List<Account>> loadExistingAccounts() {
        System.out.println("\nОТЛАДКА => Прочитать счета, существующие в хранилище");
        return RepositoryResult.success(new ArrayList<>(accountMap.values()), false);
    }

}
