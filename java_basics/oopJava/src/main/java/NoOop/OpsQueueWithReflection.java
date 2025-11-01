package NoOop;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Queue;

public class OpsQueueWithReflection {
    public static void main(String[] args) {

        System.out.println("\nСоздать счета\n");
        System.out.println("=".repeat(88));

        Account acc1 = new Account(BigDecimal.valueOf(5000.00)); // * - **Счет 1**: `1`, баланс: **5000.00 ₽**
        System.out.printf("создан счет %s, баланс %.2f ₽%n", acc1, acc1.getBalance());
        Account acc2 = new Account(BigDecimal.valueOf(3000.00)); // * - **Счет 2**: `2`, баланс: **3000.00 ₽**
        System.out.printf("создан счет %s, баланс %.2f ₽%n", acc2, acc2.getBalance());
        System.out.println("=".repeat(88));
        System.out.flush();

        System.out.println("\nСоздать очередь операций\n");
        Queue<Object> opsQueue = new LinkedList<>();
        System.out.println("=".repeat(88));

        opsQueue.add(new Deposit(BigDecimal.valueOf(800.00), acc1)); // * | 1   | **Зачисление** | Сумма: 800.00, Счет-получатель: `1`            | Счет 1: **5800.00**                      |
        opsQueue.add(new Withdraw(BigDecimal.valueOf(600.00), acc2)); // * | 2   | **Снятие**     | Сумма: 600.00, Счет-источник: `2`              | Счет 2: **2400.00**                      |
        opsQueue.add(new Transfer(BigDecimal.valueOf(1200.00), acc1, acc2)); // * | 3   | **Перевод**    | Сумма: 1200.00, Источник: `1`, Получатель: `2` | Счет 1: **4600.00**, Счет 2: **3600.00** |
        opsQueue.add(new Deposit(BigDecimal.valueOf(450.00), acc2)); // * | 4   | **Зачисление** | Сумма: 450.00, Счет-получатель: `2`            | Счет 2: **4050.00**                      |
        opsQueue.add(new Withdraw(BigDecimal.valueOf(900.00), acc1)); // * | 5   | **Снятие**     | Сумма: 900.00, Счет-источник: `1`              | Счет 1: **3700.00**                      |
        opsQueue.add(new Transfer(BigDecimal.valueOf(500.00), acc2, acc1)); // * | 6   | **Перевод**    | Сумма: 500.00, Источник: `2`, Получатель: `1`  | Счет 1: **4200.00**, Счет 2: **3550.00** |
        opsQueue.add(new Deposit(BigDecimal.valueOf(1100.00), acc1)); // * | 7   | **Зачисление** | Сумма: 1100.00, Счет-получатель: `1`           | Счет 1: **5300.00**                      |
        opsQueue.add(new Withdraw(BigDecimal.valueOf(1400.00), acc2)); // * | 8   | **Снятие**     | Сумма: 1400.00, Счет-источник: `2`             | Счет 2: **2150.00**                      |
        opsQueue.add(new Transfer(BigDecimal.valueOf(750.00), acc1, acc2)); // * | 9   | **Перевод**    | Сумма: 750.00, Источник: `1`, Получатель: `2`  | Счет 1: **4550.00**, Счет 2: **2900.00** |
        opsQueue.add(new Deposit(BigDecimal.valueOf(300.00), acc2)); // * | 10  | **Зачисление** | Сумма: 300.00, Счет-получатель: `2`            | Счет 2: **3200.00**                      |
        System.out.println("""
                Операции в очереди
                """);
        opsQueue.forEach(op -> System.out.println("  " + op.getClass().getSimpleName()));
        System.out.flush();

        System.out.println("=".repeat(88));
        System.out.println("\nИзвлекаем и выполняем операции\n");
        System.out.flush();
        while (!opsQueue.isEmpty()) {
            Object op = opsQueue.poll();
            System.out.println("\nОперация: " + op.getClass().getSimpleName());
            System.out.flush();

            Method[] allMethods = op.getClass().getDeclaredMethods(); // все методы класса операции - без конструкторов

            if (allMethods.length == 0) {
                throw new IllegalStateException("Операция " + op.getClass().getSimpleName() + " не содержит методов");
            }

            Method theOnlyPublicMethod = null;
            for (Method m : allMethods) {
                // проверяю метод на соответствие:
                // - область видимости public
                // - тип возвращаемого значения void
                // - не принимает аргументов
                if (
                        Modifier.isPublic(m.getModifiers()) &&
                                m.getReturnType().equals(Void.TYPE) &&
                                m.getParameters().length == 0
                ) {
                    theOnlyPublicMethod = m;
                    break; // требованиям отвечает только один метод, если theOnlyPublicMethod примет другой метод, то в реализации ошибка
                }
            }
            if (theOnlyPublicMethod == null) {
                throw new IllegalStateException("Операция не содержит метода, отвечающего требованиям");
            }

            try {
                theOnlyPublicMethod.invoke(op);
            } catch (Exception e) {
                throw new RuntimeException("Ошибка при вызове метода " + theOnlyPublicMethod.getName(), e);
            }

        }
        System.out.println("=".repeat(88));

        System.out.println("\nИтоговый баланс\n");
        System.out.printf("Счет %s, баланс %.2f ₽%n", acc1, acc1.getBalance());
        System.out.printf("Счет %s, баланс %.2f ₽%n", acc2, acc2.getBalance());
        System.out.println("=".repeat(88));

        System.out.println("\nВыполнение операций завершено успешно.\n");
        System.out.flush();
    }
}
