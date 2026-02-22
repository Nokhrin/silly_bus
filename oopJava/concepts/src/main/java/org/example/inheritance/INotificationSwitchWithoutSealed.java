package org.example.inheritance;

interface INotificationSwitchWithoutSealed {
    String send();
}
class EmailNoSealed implements INotificationSwitchWithoutSealed{
    public String send() {
        return "EmailNoSealed";
    }
}
class TelegramNoSealed implements INotificationSwitchWithoutSealed{
    public String send() {
        return "TelegramNoSealed";
    }

}

