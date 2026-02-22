package org.example.inheritance;

public final class IEmailNotification implements INotification {

    @Override
    public String send() {
        return "отправить email";
    }
}
