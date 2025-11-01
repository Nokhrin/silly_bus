package NoOop;

import java.math.BigDecimal;

public class OpsQueue {
    public static void main(String[] args) {
        System.out.println("""
                Выполнить очередь операций
                """);

        System.out.println("""
                1 - создать счет
                """);
        Account acc1 = new Account();


        System.out.println("""
                2 - пополнить счет
                """);
        Deposit dep1 = new Deposit(BigDecimal.valueOf(8));
        dep1.addMoneyToAccount(acc1);

        System.out.println("""
                3 - напечатать баланс счета
                """);
        System.out.println(acc1.getBalance());
    }
}
