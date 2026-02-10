package org.example.impl2;
import org.example.api.Processor;

public class ReverseProcessor implements Processor {
    @Override
    public String process(String input) {
        return new StringBuilder(input).reverse().toString();
    }

    public static void main(String[] args) {
        ReverseProcessor reverseProcessor = new ReverseProcessor();
        String input = "hello";
        System.out.println("impl2: " + input + " -> " + reverseProcessor.process(input));
    }
}
