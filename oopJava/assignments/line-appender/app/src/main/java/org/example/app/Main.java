package org.example.app;

import org.example.api.Processor;
import org.example.impl1.UppercaseProcessor;
import org.example.impl2.ReverseProcessor;

public class Main {
    public static void main(String[] args) {
        String impl = "impl1";
//        String impl = "impl2";
        
        String input = "hello";
        
        
        Processor processor = switch (impl) {
            case "impl1" -> new UppercaseProcessor();
            case "impl2" -> new ReverseProcessor();
            default -> throw new IllegalArgumentException("неизвестная операция");
        };
        
        System.out.println("результат: " + processor.process(input));
        // результат: HELLO
    }
}
