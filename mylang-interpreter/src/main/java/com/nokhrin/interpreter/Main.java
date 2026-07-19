package com.nokhrin.interpreter;

import com.nokhrin.interpreter.calc.Calculator;
import com.nokhrin.interpreter.common.EvalResult;

import java.util.Scanner;

public class Main {
    static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Calculator calculator = new Calculator();
        System.out.println("Введите выражение (\\q для выхода)");
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine().trim();
            if (input.isEmpty() || input.equals("\\q")) {
                break;
            }
            try {
                EvalResult result = calculator.parse(input);
                System.out.println(result);
            } catch (Exception e) {
                System.err.println("Ошибка вычисления: " + e);
            }
        }
    }
}
