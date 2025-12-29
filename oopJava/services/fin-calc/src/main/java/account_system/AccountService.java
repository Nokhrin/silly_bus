package account_system;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AccountService {
    private final Map<UUID, Account> accounts = new HashMap<>();
    
    public UUID openAccount(BigDecimal initialBalance) {
        Account account = new Account(initialBalance);
        accounts.put(account.getId(), account);
        return account.getId();
    }

    public Account getAccount(UUID id) {
        return accounts.get(id);
    }

    public void closeAccount(UUID id) {
        accounts.remove(id);
    }

    public void deposit(UUID accountId, BigDecimal amount) {
        Account account = getAccount(accountId);
        if (account == null) throw new IllegalArgumentException("Счет не найден");
        account.deposit(amount);
    }

    public void withdraw(UUID accountId, BigDecimal amount) {
        Account account = getAccount(accountId);
        if (account == null) throw new IllegalArgumentException("Счет не найден");
        if (account.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Недостаточно средств");
        }
        account.withdraw(amount);
    }

    public void transfer(UUID fromId, UUID toId, BigDecimal amount) {
        withdraw(fromId, amount);
        deposit(toId, amount);
    }

    public BigDecimal getBalance(UUID accountId) {
        Account account = getAccount(accountId);
        if (account == null) throw new IllegalArgumentException("Счет не найден");
        return account.getBalance();
    }

    public Map<UUID, Account> getAllAccounts() {
        return Map.copyOf(accounts);
    }
}