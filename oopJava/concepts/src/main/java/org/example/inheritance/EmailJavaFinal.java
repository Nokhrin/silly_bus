package org.example.inheritance;

public final class EmailJavaFinal{
    String send() {
        return "EmailJavaFinal";
    }
}

// ошибка компиляции: java: cannot inherit from final org.example.inheritance.EmailJavaFinal
//class NewEmailJavaFinal extends EmailJavaFinal {}