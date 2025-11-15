package MultipleInheritance.Messaging;

// абстракция - не содержит реализации, но определяет типы данных параметров и возвращаемого значения
public interface MessageSender {
    void send(String message);
}
