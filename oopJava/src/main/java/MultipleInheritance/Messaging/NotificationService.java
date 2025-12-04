package MultipleInheritance.Messaging;

// класс высокого уровня
// знаю, что могу отправить сообщение, как именно - для меня не имеет значения
public class NotificationService {
    private final MessageSender sender;

    // конструктор - внедряет зависимость
    public NotificationService(MessageSender sender) {
        this.sender = sender;
    }

    public void sendNotification(String content) {
        sender.send(content);
    }
}
