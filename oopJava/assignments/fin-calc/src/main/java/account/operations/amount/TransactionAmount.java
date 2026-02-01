package account.operations.amount;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Сумма строгоположительная.
 * Гарантирует бизнес-правила: 
 *  Сумма всегда > 0
 *  Инкапсулирует валидацию: Все проверки в одном месте
 *  Повышает читаемость: Amount amount говорит о смысле значения
 *  Предотвращает ошибки: Невозможно создать отрицательную сумму
 */
public final class TransactionAmount implements Amount {
    private final BigDecimal value;

    /**
     * Конструктор для внутренних операций над BigDecimal.
     */
    private TransactionAmount(BigDecimal value) {
        if (value == null) {
            throw new IllegalArgumentException("Сумма должна не может быть null");
        }

        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Сумма должна быть строго больше нуля");
        }
        this.value = value.stripTrailingZeros().setScale(2, RoundingMode.HALF_UP);
    }
    
    
    /**
     * Создает сумму из строки.
     * @param value
     */
    public TransactionAmount(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Сумма должна не может быть null");
        }

        BigDecimal bigDecimalValue = new BigDecimal(value);
        if (bigDecimalValue.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Сумма должна быть строго больше нуля");
        }
        this.value = bigDecimalValue.stripTrailingZeros().setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Создает BigDecimal для операций сложения и вычитания.
     * @return
     */
    public static TransactionAmount of(BigDecimal value) {
        return new TransactionAmount(value);
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
