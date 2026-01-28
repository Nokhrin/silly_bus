package account.system;

import java.util.*;

/**
 * Хранение в памяти.
 */
public final class InMemoryAccountRepository implements AccountRepository {
    private final Map<UUID, Account> accountMap = new HashMap<>();

    @Override
    public void saveAccount(Account account) {
        System.out.println("\nОТЛАДКА\nЗапись в память счета " + account.getId() + "\n");
        accountMap.put(account.getId(), account);
    }

    @Override
    public Account loadAccount(UUID accountId) {
        System.out.println("\nОТЛАДКА\nЧтение из памяти счета " + accountId + "\n");
        return accountMap.get(accountId);
    }

    @Override
    public void deleteAccount(UUID accountId) {
        System.out.println("\nОТЛАДКА\nУдаление из памяти счета " + accountId + "\n");
        accountMap.remove(accountId);
    }

    @Override
    public List<Account> loadExistingAccounts() {
        System.out.println("\nОТЛАДКА\nЧтение из памяти существующих счетов\n");
        return new ArrayList<>(accountMap.values());
    }

}
