package org.example.inheritance;

public class SenderWithoutSealed {

    String send(INotificationSwitchWithoutSealed message) {
        return switch (message) {
            case EmailNoSealed e -> "EmailNoSealed";
            case TelegramNoSealed t -> "TelegramNoSealed";
            default -> "неизвестный тип уведомления";
        };
    }

}
