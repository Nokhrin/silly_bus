package account.system;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Сервис управления счетами.
 * Выполняет бизнес-логику.
 * Сложение и вычитание балансов и сумм
 * Валидация балансов и сумм
 * Валидация логики операций
 */
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Открыть счет.
     *
     * @return ID нового счета
     */
    public UUID openAccount() {
        Account account = new Account();
        accountRepository.saveAccount(account);
        return account.getId();
    }

    /**
     * Получить счет по ID.
     *
     * @param accountId ID счета
     * @return счет
     */
    public Account getAccount(final UUID accountId) {
        return accountRepository.loadAccount(accountId);
    }

    /**
     * Получить баланс счета.
     *
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
     *
     * @return копия карты счетов
     */
    public List<Account> getAllAccounts() {
        return accountRepository.loadExistingAccounts();
    }

    /**
     * Закрыть счет.
     *
     * @param accountId ID счета
     */
    public void closeAccount(final UUID accountId) {
        accountRepository.deleteAccount(accountId);
    }

    /**
     * Пополнить счет.
     *
     * @param accountId ID счета
     * @param amount    сумма пополнения
     */
    public void deposit(final UUID accountId, final Amount amount) {
        Account account = getAccount(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Счет не найден");
        }
        account.deposit(amount);
        accountRepository.saveAccount(account);
    }

    /**
     * Снять деньги со счета.
     *
     * @param accountId ID счета
     * @param amount    сумма снятия
     */
    public void withdraw(final UUID accountId, final Amount amount) {
        Account account = getAccount(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Счет не найден");
        }
        if (account.getBalance().compareTo(amount.getValue()) < 0) {
            throw new IllegalArgumentException("Недостаточно средств");
        }
        account.withdraw(amount);
        accountRepository.saveAccount(account);
    }

    /**
     * Перевести деньги между счетами.
     *
     * @param sourceAccountId ID счета-источника
     * @param targetAccountId   ID счета-получателя
     * @param amount сумма перевода
     */
    public void transfer(
            final UUID sourceAccountId, final UUID targetAccountId, final Amount amount) {
        Account sourceAccount = getAccount(sourceAccountId);
        Account targetAccount = getAccount(targetAccountId);

        withdraw(sourceAccountId, amount);
        deposit(targetAccountId, amount);

        accountRepository.saveAccount(sourceAccount);
        accountRepository.saveAccount(targetAccount);
    }

}
