import java.math.BigDecimal;
import java.util.UUID;

public abstract class Operation {
    private UUID id;
    private BigDecimal amount;

    public void perform() {
        System.out.println("do operation");
    }

    public BigDecimal getAmount() {
        return this.amount;
    }
}
