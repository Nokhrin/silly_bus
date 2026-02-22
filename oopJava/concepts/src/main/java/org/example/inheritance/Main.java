package org.example.inheritance;

public class Main {
    public static void main(String[] args) {
        // open
        EmailJavaOpen emailJavaOpen = new EmailJavaOpen();
        System.out.println(emailJavaOpen.send());
        
        NewEmailJavaOpen newEmailJavaOpen = new NewEmailJavaOpen();
        System.out.println(newEmailJavaOpen.send());

        EmailJavaFinal emailJavaFinal = new EmailJavaFinal();
        System.out.println(emailJavaFinal.send());
         
        EmailJavaSealed emailJava = new EmailJavaSealed();
        System.out.println(emailJava.send());

        MockOpen mockOpen = new MockOpen();
        System.out.println(mockOpen.send());

        //EmailJavaOpen
        //NewEmailJavaOpen - некий наследник EmailJavaOpen
        //EmailJavaFinal
        //EmailJavaSealed
        //mock вместо super.send()
        
        // non sealed
        EmailNoSealed emailNoSealed = new EmailNoSealed();
        System.out.println(emailNoSealed.send());

        TelegramNoSealed telegramNoSealed = new TelegramNoSealed();
        System.out.println(telegramNoSealed.send());

        // sealed
        EmailSealed emailSealed = new EmailSealed();
        System.out.println(emailSealed.send());

        TelegramSealed telegramSealed = new TelegramSealed();
        System.out.println(telegramSealed.send());
        //EmailNoSealed
        //TelegramNoSealed
        //EmailSealed
        //TelegramSealed
    }
}


