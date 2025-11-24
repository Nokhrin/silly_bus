package FinOps;

import java.util.Optional;

public class FinMain {
    public static void main(String[] args) {
        String input = "deposit 12345";

        Optional<Integer> result = NumberParser.parseInteger(input, 0);
        
        System.out.println(result);
    }
}
