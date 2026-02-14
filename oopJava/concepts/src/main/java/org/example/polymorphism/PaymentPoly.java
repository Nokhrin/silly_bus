/**
 * разные реализации метода с одной сигнатурой
 */
package polymorphism;

// тип PaymentPoly объявляет операцию, доступную для использования 
interface PaymentPoly {
    // единая сигнатура
    boolean process();
}

class CardPaymentPoly implements PaymentPoly {

    // реализация 1
    @Override
    public boolean process() {
        System.out.println("оплата картой");
        return true;
    }
}

class CashPaymentPoly implements PaymentPoly {

    // реализация 2
    @Override
    public boolean process() {
        System.out.println("оплата наличными");
        return true;
    }
}


class Main {
    public static void main(String[] args) {
        PaymentPoly paymentCard = new CardPaymentPoly();
        paymentCard.process();
        
        PaymentPoly paymentCash = new CashPaymentPoly();
        paymentCash.process();
        
                
    }
}