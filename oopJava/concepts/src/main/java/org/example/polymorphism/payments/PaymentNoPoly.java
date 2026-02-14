package main.java.org.example.polymorphism.payments;

/**
 * для каждой задачи - свой метод
 */
interface PaymentNoPoly {
    public boolean processCardPayment();

    public boolean processCashPayment();
}

class Payment implements PaymentNoPoly {

    @Override
    public boolean processCardPayment() {
        System.out.println("оплата картой");
        return false;
    }

    @Override
    public boolean processCashPayment() {
        System.out.println("оплата наличными");
        return false;
    }
}
