package ParsingDraft;

import java.math.BigDecimal;

public class OperationService {
    private BigDecimal balance = BigDecimal.ZERO;

    public void execute(Operation operation) {
        switch (operation.getType()) {
            case Deposit -> balance = balance.add(operation.getAmount());
            case Withdraw -> {
                if (balance.compareTo(operation.getAmount()) > 0) {
                    throw new RuntimeException("Недостаточно средств для снятия");
                }
                balance = balance.subtract(operation.getAmount());
            }
            case Transfer -> {
                BigDecimal amount = operation.getAmount();
                long from = operation.getSourceAccount();
                long to = operation.getTargetAccount();

                if (balance.compareTo(operation.getAmount()) > 0) {
                    throw new RuntimeException("Недостаточно средств для перевода");
                }
                balance = balance.subtract(amount);
                System.out.println("Перевод " + amount + " с счёта " + from + " на счёт " + to);
            }
        }
        System.out.println("Баланс: " + balance);
    }
}
