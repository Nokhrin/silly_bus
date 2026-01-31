package account.system.cli;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        
        CommandLineProcessor commandLineProcessor = new CommandLineProcessor(scanner);
        commandLineProcessor.start();
        
        // Ручная проверка
        // Интерактивный режим
        
        // открыть счет 1 - успех
        // open
        // ok 1c471d4f-f241-4fbb-92f7-2e40c277012a
        
        // баланс счета 1 - успех
        // balance 1c471d4f-f241-4fbb-92f7-2e40c277012a
        // ok 0

        // открыть счет 2 - успех
        // open
        // ok 344c81a5-9256-45a4-af81-37fa147d47f7
        
        // баланс счета 2 - успех
        // balance 344c81a5-9256-45a4-af81-37fa147d47f7
        // ok 0

        // список счетов - существуют 2 счета - успех
        // list
        // ok 344c81a5-9256-45a4-af81-37fa147d47f7 b511e9c8-842f-4397-8097-ebbe1ff30b07
        
        // зачисление на счет 1 - успех
        // deposit 344c81a5-9256-45a4-af81-37fa147d47f7 100
        // ok

        // снятие со счета 1 - успех
        // withdraw 344c81a5-9256-45a4-af81-37fa147d47f7 50
        // ok

        // перевод со счета 1 на счет 2
        // transfer 344c81a5-9256-45a4-af81-37fa147d47f7 b511e9c8-842f-4397-8097-ebbe1ff30b07 30.0
        // ok
        
        // баланс счета 1 - успех
        // balance 344c81a5-9256-45a4-af81-37fa147d47f7
        // ok 20.00

        // баланс счета 2 - успех
        // balance b511e9c8-842f-4397-8097-ebbe1ff30b07
        // ok 30.00

        // закрытие счета 1 - успех
        // close 344c81a5-9256-45a4-af81-37fa147d47f7
        // ok

        // список счетов - отсутствует закрытый счет - успех
        // list
        // ok b511e9c8-842f-4397-8097-ebbe1ff30b07
        
        // TODO
        // закрытие несуществующего счета - неудача
        // close 344c81a5-9256-45a4-af81-37fa147d47f7
        // 

        // снятие с несуществующего счета - неудача
        // 

        // зачисление на несуществующий счет - неудача
        // 

        // баланс несуществующего счета - неудача
        // 

        // снятие с существующего счета, amount > balance - неудача
        // 

        // закрыть счет - нарушение формата ввода - неудача
        // close
        // err некорректный ввод

        // закрыть счет - нарушение формата ввода - неудача
        // close 1
        // err некорректный ввод
    }
}
