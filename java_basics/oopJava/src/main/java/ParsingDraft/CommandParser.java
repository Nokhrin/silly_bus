package ParsingDraft;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandParser {
    private static final Pattern PATTERN_DEPOSIT = Pattern.compile("^deposit\\s+(\\d+)$");
    private static final Pattern PATTERN_WITHDRAW = Pattern.compile("^withdraw\\s+(\\d+)$");
    private static final Pattern PATTERN_TRANSFER = Pattern.compile("^transfer\\s+(\\d+)\\s+from\\s+(\\d+)\\s+to\\s+(\\d+)$");

    public Operation parse(String command) {
        command = command.trim();

        // Пополнить
        Matcher m1 = PATTERN_DEPOSIT.matcher(command);
        if (m1.matches()) {
            BigDecimal amount = BigDecimal.valueOf(Long.parseLong(m1.group(1)));
            return new Operation(OperationType.Deposit, amount, 0, 0);
        }

        // Снять
        Matcher m2 = PATTERN_WITHDRAW.matcher(command);
        if (m2.matches()) {
            BigDecimal amount = BigDecimal.valueOf(Long.parseLong(m2.group(1)));
            return new Operation(OperationType.Withdraw, amount, 0, 0);
        }

        // Перевести
        Matcher m3 = PATTERN_TRANSFER.matcher(command);
        if (m3.matches()) {
            BigDecimal amount = BigDecimal.valueOf(Long.parseLong(m3.group(1)));
            long source = Long.parseLong(m3.group(2));
            long target = Long.parseLong(m3.group(3));
            return new Operation(OperationType.Transfer, amount, source, target);
        }

        throw new IllegalArgumentException("Неизвестная команда: " + command);
    }
}
