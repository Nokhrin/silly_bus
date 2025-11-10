package DemoTyping;

import java.util.ArrayList;
import java.util.List;

public class SubTypes {
    public static void main(String[] args) {
        Operation operation_1 = new Operation();
        Operation operation_2 = new Operation();

        Deposit operation_3 = new Deposit();
        Deposit operation_4 = new Deposit();

        List<Operation> ops = new ArrayList<>();

        // очевидно по List<Operation>
        // список допускает вхождение объектов типа Operation
        ops.add(operation_1);
        ops.add(operation_2);

        // следствие ковариации
        // - List<Operation> допускает включение подтипа - Deposit
        // не требуется отдельный список List<Deposit>
        ops.add(operation_3);
        ops.add(operation_4);

        // после компиляции имен переменных не существует
        // подставляю индексы вручную
        System.out.println("Номер операции - Адрес объекта в памяти - Тип объекта");
        for (int i = 0; i < ops.size(); i++) {
            Object obj = ops.get(i);
            String typeName = obj.getClass().getSimpleName();
            String objectName = "operation_" + (i + 1);
            System.out.println(objectName + " = " + obj + " (тип: " + typeName + ")");
        }
        /*
        Номер операции - Адрес объекта в памяти - Тип объекта
        operation_1 = DemoTyping.Operation@8efb846 (тип: Operation)
        operation_2 = DemoTyping.Operation@2a84aee7 (тип: Operation)
        operation_3 = DemoTyping.Deposit@a09ee92 (тип: Deposit)
        operation_4 = DemoTyping.Deposit@30f39991 (тип: Deposit)
         */
    }
}
