package org.example.inheritance;

sealed interface INotificationSwitchWithSealed permits EmailSealed, TelegramSealed{
    String send();
}
final class EmailSealed implements INotificationSwitchWithSealed {
    @Override
    public String send() {
        return "EmailSealed";
    }
}
final class TelegramSealed implements INotificationSwitchWithSealed {
    @Override
    public String send() {
        return "TelegramSealed";
    }
}

