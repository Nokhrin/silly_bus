package account.operations.amount;

import java.math.BigDecimal;

/**
 * Тип суммы банковской операции.
 * Гарантирует бизнес-правила: 
 *  Сумма всегда > 0
 *  Инкапсулирует валидацию: Все проверки в одном месте
 *  Повышает читаемость: Amount amount говорит о смысле значения
 *  Предотвращает ошибки: Невозможно создать отрицательную сумму
 *  todo
 */
sealed public interface Amount permits PositiveAmount {
    BigDecimal getValue();
    Amount add(Amount other);
    Amount sub(Amount other);
}
