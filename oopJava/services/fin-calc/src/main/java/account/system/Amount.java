package account.system;

import java.math.BigDecimal;

/**
 * Тип суммы банковской операции.
 * Гарантирует, что значение всегда строго больше нуля.
 */
public class Amount extends BigDecimal {

    private static final long serialVersionUID = 1L;

    /**
     * Создает Amount с заданным значением.
     * @param value сумма
     */
    public Amount(String value) {
        super(value);
        validate(this);
    }

    /**
     * Создает Amount с заданным значением.
     * @param value сумма
     */
    public Amount(BigDecimal value) {
        super(value.stripTrailingZeros().toString());
        validate(this);
    }
    
    /**
     * Создает Amount с заданным значением.
     * @param value сумма
     */
    public Amount(int value) {
        this(BigDecimal.valueOf(value));
    }

    /**
     * Валидация значения.
     * @param value значение для проверки
     */
    private static void validate(BigDecimal value) {
        if (value == null) {
            throw new IllegalArgumentException("Сумма не может быть null");
        }
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Сумма должна быть строго больше нуля");
        }
    }

    /**
     * Сложение Amount с другим Amount.
     * @param other другой Amount
     * @return Amount
     */
    public Amount add(Amount other) {
        return new Amount(super.add(other));
    }

    /**
     * Вычитание Amount из другого Amount.
     * @param other другой Amount
     * @return Amount
     */
    public Amount subtract(Amount other) {
        return new Amount(super.subtract(other));
    }
}