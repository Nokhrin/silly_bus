package main.java.org.example.polymorphism.payments;

/**
 * Демо open/closed principle
 */
public class AccountOCP {
    private double balance;
    
    public double deposit(double amount) {
        balance += amount;
        return balance;
    }
}
