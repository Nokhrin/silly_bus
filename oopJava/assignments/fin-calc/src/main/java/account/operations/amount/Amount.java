package account.operations.amount;

import java.math.BigDecimal;

/**
 * Тип суммы банковской операции.
 */
sealed public interface Amount permits TransactionAmount, BalanceAmount {
    BigDecimal getValue();
    Amount add(Amount other);
    Amount sub(Amount other);
}
