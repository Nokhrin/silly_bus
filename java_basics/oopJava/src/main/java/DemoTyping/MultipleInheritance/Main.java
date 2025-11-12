package DemoTyping.MultipleInheritance;
/*

 */
public class Main {
    public static void main(String[] args) {
        Transaction transaction = new Transaction(100);

        transaction.deposit(30);
        transaction.withdraw(20);
        System.out.println(transaction.getId());
        System.out.println(transaction.getAmount());
    }
}
