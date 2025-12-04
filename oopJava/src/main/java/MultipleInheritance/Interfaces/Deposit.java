package MultipleInheritance.Interfaces;

public class Deposit extends Operation implements PaymentProcessor {
    public void perform() {
        System.out.println("выполняю зачисление");
    }
}
