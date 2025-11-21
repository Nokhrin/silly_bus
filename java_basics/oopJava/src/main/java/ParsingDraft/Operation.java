package ParsingDraft;

import java.math.BigDecimal;

public class Operation {
    private final OperationType type;
    private final BigDecimal amount;
    private final long sourceAccount;
    private final long targetAccount;

    public Operation(OperationType type, BigDecimal amount, long sourceAccount, long targetAccount) {
        this.type = type;
        this.amount = amount;
        this.sourceAccount = sourceAccount;
        this.targetAccount = targetAccount;
    }

    public OperationType getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public long getSourceAccount() {
        return sourceAccount;
    }

    public long getTargetAccount() {
        return targetAccount;
    }

    public String toString() {
        return String.format("Операция{тип:%s,сумма:%s,источник:%s,получатель:%s}", type, amount, sourceAccount, targetAccount);
    }
}
