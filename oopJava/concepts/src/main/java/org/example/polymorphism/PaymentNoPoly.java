package main.java.org.example.polymorphism;

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


class Main {
    public static void main(String[] args) {
        Payment payment1 = new Payment();

        String userInput = "безнал, пожалуйста";

        if (userInput.equalsIgnoreCase("безнал, пожалуйста")) {
            payment1.processCardPayment();

        } else {
            payment1.processCashPayment();
        }
    }
}