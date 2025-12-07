package MultipleInheritance.Messaging;

public class Main {
    public static void main(String[] args) {
        SMSSender sender = new SMSSender();
        NotificationService service = new NotificationService(sender);
        service.sendNotification("спасибо за обращение");
        // отправляю sms:
        // спасибо за обращение
    }
}
