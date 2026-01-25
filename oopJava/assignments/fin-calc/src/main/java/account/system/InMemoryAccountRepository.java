package account.system;

import java.util.*;

/**
 * Хранение в памяти.
 */
public final class InMemoryAccountRepository implements AccountRepository {
    private final Map<UUID, Account> accountMap = new HashMap<>();

    @Override
    public void saveAccount(Account account) {
        System.out.println("Запись в память счета " + account.getId());
        accountMap.put(account.getId(), account);
    }

    @Override
    public Account loadAccount(UUID accountId) {
        System.out.println("Чтение из памяти счета " + accountId);
        return accountMap.get(accountId);
    }

    @Override
    public void deleteAccount(UUID accountId) {
        System.out.println("Удаление из памяти счета " + accountId);
        accountMap.remove(accountId);
    }

    @Override
    public List<Account> loadExistingAccounts() {
        System.out.println("Чтение из памяти существующих счетов");
        return new ArrayList<>(accountMap.values());
    }

}
