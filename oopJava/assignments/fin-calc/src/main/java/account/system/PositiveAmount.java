package account.system;

import java.math.BigDecimal;

public final class PositiveAmount implements Amount {
    private final BigDecimal value; // Amount содержит BigDecimal, не наследует
    
    // валидация значения при создании экземпляра
    PositiveAmount(BigDecimal value) {
        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Сумма должна быть строго больше нуля");
        }
        this.value = value.stripTrailingZeros();
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
