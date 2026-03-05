package main.java;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class TransactionsRepo {

    private final Connection connection;
    private static final Logger logger = Logger.getLogger(TransactionsRepo.class.getName());

    public TransactionsRepo(Connection connection) {
        this.connection = connection;
    }

    // Имитация выборки данных
    public List<TransactionPojo> fetchPending() throws SQLException {
        logger.info("мок чтения из бд");
        return Arrays.asList(
                new TransactionPojo(1, "ACTIVE", 100.0, "IT"),
                new TransactionPojo(2, "ARCHIVED", 50.0, "IT"),
                new TransactionPojo(3, "ACTIVE", -20.0, "HR"),
                new TransactionPojo(4, "ACTIVE", 200.0, "SALES")
        );
    }

    public void updateStatus(int id, String newStatus) throws SQLException {
        logger.info("запись: " + id + ", статус изменен на " + newStatus);
    }
}
