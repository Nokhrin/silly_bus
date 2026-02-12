package org.example.api;

import java.math.BigDecimal;


/**
 * укрепленный объект
 */
public class AccountSafe {
    /**
     * поле доступно из экземпляра
     */
    private BigDecimal balance;

    /**
     * значения проверяются, возвращается результат успех/неуспех
     * @param amount
     */
    public boolean withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0 || amount.compareTo(balance) > 0) {
            return false;
        }
        balance.subtract(amount);
        return true;
    }
}

