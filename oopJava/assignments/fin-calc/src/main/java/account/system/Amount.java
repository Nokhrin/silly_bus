package account.system;

import java.math.BigDecimal;

/**
 * Тип суммы банковской операции.
 * Гарантирует бизнес-правила: 
 *  Сумма всегда > 0
 *  Инкапсулирует валидацию: Все проверки в одном месте
 *  Повышает читаемость: Amount amount говорит о смысле значения
 *  Предотвращает ошибки: Невозможно создать отрицательную сумму
 */
sealed public interface Amount permits PositiveAmount {
    BigDecimal getValue();
    
    static Amount of(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Сумма не может быть null");
        }
        try {
            BigDecimal bigDecimal = new BigDecimal(value);
            return new PositiveAmount(bigDecimal);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Неверный формат суммы: " + value, e);
        }
    }
    
    Amount add(Amount other);
    Amount sub(Amount other);
}
