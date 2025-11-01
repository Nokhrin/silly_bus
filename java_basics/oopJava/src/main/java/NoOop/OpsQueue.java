package NoOop;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class OpsQueue {
    public static void main(String[] args) {

        System.out.println("Создать очередь операций");
        /*
         * чтобы создать  очередь (Queue) объектов  разных типов,
         * нужно использовать обобщения ( Generic) и
         * подходящую реализацию интерфейса  Queue<E>
         */
        Queue<Object> opsQueue = new LinkedList<>();

        System.out.println("создать счет 1");
        System.out.flush();
        Account acc1 = new Account();

        System.out.println("создать счет 2");
        System.out.flush();
        Account acc2 = new Account();

        System.out.println("Поставить операции в очередь");
        System.out.flush();
        opsQueue.add(new Deposit(BigDecimal.valueOf(8)));   // пополнение 8
        opsQueue.add(new Withdraw(BigDecimal.valueOf(4)));   // снятие 4
        opsQueue.add(new Transfer(BigDecimal.valueOf(2)));   // перевод 2


        System.out.println("Извлекаем и выполняем операции");
        while (!opsQueue.isEmpty()) {
            Object op = opsQueue.poll();
            System.out.println("Операция: " + op.getClass().getSimpleName() +
                    "\nполя: " + Arrays.toString(op.getClass().getDeclaredFields()));
            System.out.flush();
        }

    }
}
