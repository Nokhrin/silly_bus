package account_system;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Счет
 * - это уникальная запись которая отображает кол - во денег
 *
 * Операции со счетом
 * • Пополнение
 * ◦ нельзя пополнить счет суммой 0 или меньше
 * • Снятие
 * ◦ нельзя снять денег, больше чем есть на балансе
 * ◦ снятие денег возможно при положительном балансе
 * • Перевод - с одного счета, другому
 * ◦ Равнозначно операциям снятия и пополнения
 * • Дополнительные операции
 * ◦ Заем/Кредит
 * ◦ Списание процентов по кредиту
 * ◦ Просрочка
 */
public class Account {
    private final UUID id = UUID.randomUUID();
    private BigDecimal balance = BigDecimal.ZERO;

    public Account(BigDecimal amount) {
        this.balance = amount;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public void deposit(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        this.balance = this.balance.subtract(amount);
    }

    public UUID getId() {
        return id;
    }
}
