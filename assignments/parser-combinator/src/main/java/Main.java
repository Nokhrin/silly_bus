import java.util.Scanner;

public class Main {
    static void main(String[] args) {
        var calculator = new Calculator();
        try (var scanner = new Scanner(System.in)) {
            System.out.println("Выражение: ");
            if (scanner.hasNextLine()) {
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.err.println("Ошибка: пустой ввод");
                    return;
                }
                System.out.println("Результат: " + calculator.calculate(input));
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Синтаксическая ошибка: " + e);
        } catch (ArithmeticException e) {
            System.err.println("Ошибка вычисления: " + e);
        } catch (Exception e) {
            System.err.println("Неизвестная ошибка: " + e);
        }
    }
}
