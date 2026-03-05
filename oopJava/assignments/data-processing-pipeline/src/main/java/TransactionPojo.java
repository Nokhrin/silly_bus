package main.java;

public class TransactionPojo {
    public final int id;
    public final String status; // ACTIVE, ARCHIVED
    public final double amount;
    public final String category;

    public TransactionPojo(int id, String status, double amount, String category) {
        this.id = id;
        this.status = status;
        this.amount = amount;
        this.category = category;
    }

}
