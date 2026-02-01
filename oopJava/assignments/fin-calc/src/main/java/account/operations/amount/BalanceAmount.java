package account.operations.amount;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Сумма баланса.
 * Может быть нулевой и отрицательной.
 */
public final class BalanceAmount implements Amount {
    private final BigDecimal value;

    /**
     * Конструктор для внутренних операций над BigDecimal.
     */
    private BalanceAmount(BigDecimal value) {
        if (value == null) {
            throw new IllegalArgumentException("Сумма должна не может быть null");
        }
        this.value = value.stripTrailingZeros().setScale(2, RoundingMode.HALF_UP);
    }


    /**
     * Создает сумму из строки.
     * @param value
     */
    public BalanceAmount(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Сумма должна не может быть null");
        }
        BigDecimal bigDecimalValue = new BigDecimal(value);
        this.value = bigDecimalValue.stripTrailingZeros().setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Создает BigDecimal для операций сложения и вычитания.
     * @return
     */
    public static BalanceAmount of(BigDecimal value) {
        return new BalanceAmount(value);
    }

    @Override
    public BigDecimal getValue() {
        return this.value;
    }

    @Override
    public Amount add(Amount other) {
        return TransactionAmount.of(this.value.add(other.getValue()));
    }

    @Override
    public Amount sub(Amount other) {
        return TransactionAmount.of(this.value.subtract(other.getValue()));
    }
}
