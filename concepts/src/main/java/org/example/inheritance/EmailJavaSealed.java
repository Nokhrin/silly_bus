package org.example.inheritance;

// ошибка - java: sealed class must have subclasses
//sealed public class EmailJavaSealed implements INotification {
//    @Override
//    public String send() {
//        return "email java";
//    }
//}

public sealed class EmailJavaSealed implements INotification {
    @Override
    public String send() {
        return "EmailJavaSealed";
    }

    // для избежания ошибки java: sealed class must have subclasses
    public final class EmailJavaImpl extends EmailJavaSealed {
    }

}


// не разрешен в INotification => избежать непреднамеренного наследования
//class NewNotification extends INotification {
//    @Override
//    public String send() {
//        return "";
//    }
//}