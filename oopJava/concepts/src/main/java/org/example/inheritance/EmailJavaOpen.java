package org.example.inheritance;

public class EmailJavaOpen {
    String send() {
        return "EmailJavaOpen";
    }
}

// допускается компилятором
class NewEmailJavaOpen extends EmailJavaOpen {
    String send() {
        return "NewEmailJavaOpen - некий наследник EmailJavaOpen";
    }
}