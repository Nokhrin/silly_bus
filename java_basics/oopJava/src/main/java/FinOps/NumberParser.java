package FinOps;

import java.util.Optional;

public class NumberParser {
    public static Optional<Integer> parseInteger(String input, int offset) {
        if (input == null || offset < 0 || offset >= input.length()) {
            return Optional.empty();
        }
        
        int index = offset;
        int start = index;
        
        int value = 0;
        while (index < input.length()) {
            char ch = input.charAt(index);
            if (ch < '0' || ch > '9') {
                break;
            }
            int digit = ch - '0';
            index++;
        }
        if (index == start) {
            System.out.println("цифры не найдены");
            return Optional.empty();
        }
        
        return Optional.of(value);
    }
}
