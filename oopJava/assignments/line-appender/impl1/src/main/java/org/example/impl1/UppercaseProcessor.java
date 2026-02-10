package org.example.impl1;

public class UppercaseProcessor implements org.example.api.Processor {

    @Override
    public String process(String input) {
        return input.toUpperCase();
    }

    public static void main(String[] args) {
        UppercaseProcessor uppercaseProcessor = new UppercaseProcessor();
        String input = "helLO";
        System.out.println("uppercaseProcessor: " + input + " -> " + uppercaseProcessor.process(input));
        // uppercaseProcessor: helLO -> HELLO
    }
}


