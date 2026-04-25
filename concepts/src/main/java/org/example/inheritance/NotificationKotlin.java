package org.example.inheritance;

/**
 * имитация класса kotlin по умолчанию
 * - final - модификатор по умолчанию в kotlin
 */
public final class NotificationKotlin {
    private String message;
    
    public String send(String message) {
        return message;
    }
}


/**
 * попытка наследования final класса => ошибка компиляции
 */
//class EmailNotification extends NotificationKotlin {
//    
//}