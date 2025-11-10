package DemoTyping.MultipleInheritance;

import java.util.UUID;

public class Transaction implements Operation, Deposit, Withdrawal {

    private double amount;
    private String id;

    public Transaction(double initialBalance) {
        this.amount = initialBalance;
        this.id = String.valueOf(UUID.randomUUID());
    }

    @Override
    public void perform() {
        if (amount > 0) {
            deposit(amount);
        } else {
            withdraw(-amount);
        }
    }

    public double getAmount() {
        return amount;
    }

    public String getId() {
        return id;
    }

    @Override
    public void deposit(double amount) {
        System.out.println("выполняю ЗАЧИСЛЕНИЕ " + amount + " единиц");
    }

    @Override
    public boolean isDeposit() {
        return amount > 0;
    }

    @Override
    public void withdraw(double amount) {
        System.out.println("выполняю СНЯТИЕ " + amount + " единиц");

    }

    @Override
    public boolean isWithdrawal() {
        return amount < 0;
    }
}