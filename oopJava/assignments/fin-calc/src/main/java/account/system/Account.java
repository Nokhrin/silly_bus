package account.system;

import account.operations.amount.PositiveAmount;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Счет.
 * inc: поля только для чтения, скрытым геттером
 * srp: только данные счета
 * liskov: Account можно использовать для объектов, ожидающих тип Account
 * ocp: функционал добавлен с помощью методов
 * imm: не изменяется после создания, выполнение операции над счетом порождает новый экземпляр счета
 */
public record Account(
        UUID id,
        BigDecimal balance
) {
    

    /**
     * Конструктор по умолчанию.
     * Случайный id при открытии счета
     */
    public Account() {
        this(UUID.randomUUID(), BigDecimal.ZERO);
    }

    /**
     * Конструктор по требованию.
     * Известный id при открытии счета
     * Для тестов
     */
    public Account(UUID id) {
        this(id, BigDecimal.ZERO);
    }

    /**
     * Зачисление.
     * @param amount
     * @return
     */
    public Account deposit(PositiveAmount amount) {
        return new Account(id, balance.add(amount.getValue()));
    }

    /**
     * Списание.
     * @param amount
     * @return
     */
    public Account withdraw(PositiveAmount amount) {
        if (balance.compareTo(amount.getValue()) < 0) {
            return this;
        };
        return new Account(id, balance.subtract(amount.getValue()));
    }

}
