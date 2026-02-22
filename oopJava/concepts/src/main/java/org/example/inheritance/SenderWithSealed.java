package org.example.inheritance;

public class SenderWithSealed {

    String send(INotificationSwitchWithSealed message) {
        return switch (message) {
            case EmailSealed e -> "EmailNoSealed";
            case TelegramSealed t -> "TelegramNoSealed";
        };
    }

}
