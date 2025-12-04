package MultipleInheritance;

import java.math.BigDecimal;
import java.util.UUID;

class TransferDemo {
    void perform(BigDecimal amount, UUID source, UUID target) {
        System.out.println("перевод " + amount + " по номеру счета");
    }

    void perform(BigDecimal amount, String source, String target) {
        System.out.println("перевод " + amount + " по номеру телефона");
    }
}

public class OperationOverloading {
    public static void main(String[] args) {
        TransferDemo transferDemo = new TransferDemo();
        BigDecimal payCheck = BigDecimal.valueOf(100);
        UUID personId1 = UUID.randomUUID();
        String personPhone1 = "7654321";
        UUID personId2 = UUID.randomUUID();
        String personPhone2 = "1234567";

        transferDemo.perform(payCheck, personId1, personId2);
        transferDemo.perform(payCheck, personPhone1, personPhone2);
    }
}
