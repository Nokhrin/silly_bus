package account.system;

import java.util.*;

/**
 * Хранение в памяти.
 */
public final class InMemoryAccountRepository implements AccountRepository {
    private final Map<UUID, Account> accountMap = new HashMap<>();

    @Override
    public void saveAccount(Account account) {
        System.out.println("\nОТЛАДКА => Записать в хранилище счет: " + account.getId());
        accountMap.put(account.getId(), account);
    }

    @Override
    public Optional<Account> loadAccount(UUID accountId) {
        System.out.println("\nОТЛАДКА => Прочитать из хранилища счет: " + accountId);
        return Optional.ofNullable(accountMap.get(accountId));
    }

    @Override
    public void deleteAccount(UUID accountId) {
        System.out.println("\nОТЛАДКА => Удалить из хранилища счет: " + accountId);
        accountMap.remove(accountId);
    }

    @Override
    public List<Account> loadExistingAccounts() {
        System.out.println("\nОТЛАДКА => Прочитать счета, существующие в хранилище");
        return new ArrayList<>(accountMap.values());
    }

}
