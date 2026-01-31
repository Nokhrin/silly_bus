package account.operations.amount;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class PositiveAmount implements Amount {
    private final BigDecimal value; // Amount содержит BigDecimal, не наследует
    
    // валидация значения при создании экземпляра
    public PositiveAmount(BigDecimal value) {
        if (value == null) {
            throw new IllegalArgumentException("Сумма должна не может быть null");
        }
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Сумма должна быть строго больше нуля");
        }
        this.value = value.stripTrailingZeros().setScale(2, RoundingMode.HALF_UP);
    }

    // валидация значения при создании экземпляра
    public PositiveAmount(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Сумма должна не может быть null");
        }

        BigDecimal bigDecimalValue = new BigDecimal(value);
        if (bigDecimalValue.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Сумма должна быть строго больше нуля");
        }
        this.value = bigDecimalValue.stripTrailingZeros().setScale(2, RoundingMode.HALF_UP);
    }

        @Override
    public BigDecimal getValue() {
        return this.value;
    }

    @Override
    public Amount add(Amount other) {
        return new PositiveAmount(this.value.add(other.getValue()));
    }

    @Override
    public Amount sub(Amount other) {
        return new PositiveAmount(this.value.subtract(other.getValue()));
    }
}
