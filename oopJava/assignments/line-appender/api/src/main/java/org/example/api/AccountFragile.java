package org.example.api;

import java.math.BigDecimal;

/**
 * хрупкий объект
 */
public class AccountFragile {
    /**
     * поле доступно внешнему коду
     */
    public BigDecimal balance;

    /**
     * нет валидации значений параметра и результата
     * @param amount
     */
    public void withdraw(BigDecimal amount) {
        balance.subtract(amount);
    }
}
