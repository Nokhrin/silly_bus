package account.system;

import java.math.BigDecimal;
import java.math.BigInteger;
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
    private BigDecimal balance = new BigDecimal(BigInteger.ZERO);

    public Account() {
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public void deposit(Amount amount) {
        this.balance = this.balance.add(amount.getValue());
    }

    public void withdraw(Amount amount) {
        this.balance = this.balance.subtract(amount.getValue());
    }

    public UUID getId() {
        return this.id;
    }
}
