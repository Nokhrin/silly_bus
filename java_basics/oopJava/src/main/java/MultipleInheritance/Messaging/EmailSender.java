package MultipleInheritance.Messaging;

public class EmailSender implements MessageSender {
    @Override
    public void send(String message) {
        System.out.println("отправляю email:\n" + message);
    }
}
