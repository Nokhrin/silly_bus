package main.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Logger;

/**
 * Этап 1: Базовая фильтрация и трансформация (Expression & Block)
 *
 * Задача: Создать процессор списка объектов Record.
 * Требования:
 *     Фильтрация записей по полю status (использовать Predicate<T> с лямбда-выражением (r) -> r.status == ACTIVE).
 *     Трансформация отфильтрованных записей в строки (использовать Function<T, R> с блоком кода { if (r.val > 0) return ...; else ...; }).
 * Критерий отказа: Без лямбд потребуется создать 2 отдельных класса implements Predicate/Function для каждого сценария обработки.
 */
public class TransactionProcessor {
    
    private static final Logger logger = Logger.getLogger("Процессор транзакций");
    
    private final TransactionsRepo repo;

    public TransactionProcessor(TransactionsRepo repo) {
        this.repo = repo;
    }
    
    
    public void run() throws SQLException {
        List<TransactionPojo> src = repo.fetchPending();
        
        // лямбда как фильтр
        Predicate<TransactionPojo> isActive = (rec) -> rec.status.equalsIgnoreCase("active");
        
        // лямбда для валидации значения, форматирования
        Function<TransactionPojo, String> convertForReport = (rec) -> {
            if (rec.amount > 0) {
                return String.format("ID:%d | Income: %.2f | Cat:%s", rec.id, rec.amount, rec.category);
            } else {
                return String.format("ID:%d | Expense: %.2f | Cat:%s", rec.id, Math.abs(rec.amount), rec.category);
            }
        };

        // лямбда для форматирования
        Consumer<String> writeToLog = (rec) -> logger.info("обработана запись: " + rec);
        
        // stream
        src.stream().filter(isActive).map(convertForReport).forEach(writeToLog);
    }

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
        TransactionsRepo transactionsRepo = new TransactionsRepo(connection);
        TransactionProcessor transactionProcessor = new TransactionProcessor(transactionsRepo);
        transactionProcessor.run();
    }
}
