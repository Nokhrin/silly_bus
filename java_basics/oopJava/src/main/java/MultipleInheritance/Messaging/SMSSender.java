package MultipleInheritance.Messaging;

public class SMSSender implements MessageSender {
    @Override
    public void send(String message) {
        System.out.println("отправляю sms:\n" + message);
    }
}
