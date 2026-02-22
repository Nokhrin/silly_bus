package org.example.inheritance;

public class MockOpen extends EmailJavaOpen {
    @Override
    String send() {
        return "mock вместо super.send()";
    }
}
