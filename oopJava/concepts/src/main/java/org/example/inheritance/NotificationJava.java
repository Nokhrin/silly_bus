package org.example.inheritance;

/**
 * класс Java по умолчанию not final, открыт для наследования
 */
public class NotificationJava {
    String message;
    
    public String send(String message) {
        return message;
    }
}


/**
 * наследование допускается
 */
class EmailNotification extends NotificationJava {
    private String emailAddress;
    
    public String send() {
        return emailAddress + " : " + message;
    }
}
