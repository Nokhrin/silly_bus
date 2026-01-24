package account.system;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Сервис управления счетами.
 */
public class AccountService {
    /**
     * Хранит все счета с их ID.
     */
    private final Map<UUID, Account> accounts = new HashMap<>();

    /**
     * Открыть новый счет.
     * @return ID нового счета
     */
    public UUID openAccount() {
        Account account = new Account((Amount) BigDecimal.ZERO);
        accounts.put(account.getId(), account);
        return account.getId();
    }

    /**
     * Получить счет по ID.
     * @param id ID счета
     * @return счет или null, если не найден
     */
    public Account getAccount(final UUID id) {
        return accounts.get(id);
    }

    /**
     * Закрыть счет.
     * @param id ID счета
     */
    public void closeAccount(final UUID id) {
        accounts.remove(id);
    }

    /**
     * Пополнить счет.
     * @param accountId ID счета
     * @param amount сумма пополнения
     */
    public void deposit(final UUID accountId, final BigDecimal amount) {
        Account account = getAccount(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Счет не найден");
        }
        account.deposit(amount);
    }

    /**
     * Снять деньги со счета.
     * @param accountId ID счета
     * @param amount сумма снятия
     */
    public void withdraw(final UUID accountId, final BigDecimal amount) {
        Account account = getAccount(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Счет не найден");
        }
        if (account.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Недостаточно средств");
        }
        account.withdraw(amount);
    }

    /**
     * Перевести деньги между счетами.
     * @param fromId ID счета-источника
     * @param toId ID счета-получателя
     * @param amount сумма перевода
     */
    public void transfer(
            final UUID fromId, final UUID toId, final BigDecimal amount) {
        withdraw(fromId, amount);
        deposit(toId, amount);
    }

    /**
     * Получить баланс счета.
     * @param accountId ID счета
     * @return баланс счета
     */
    public BigDecimal getBalance(final UUID accountId) {
        Account account = getAccount(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Счет не найден");
        }
        return account.getBalance();
    }

    /**
     * Получить все счета.
     * @return копия карты счетов
     */
    public Map<UUID, Account> getAllAccounts() {
        return Map.copyOf(accounts);
    }
}
