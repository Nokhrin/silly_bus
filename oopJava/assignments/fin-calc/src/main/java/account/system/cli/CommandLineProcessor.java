package account.system.cli;

import account.operation.Operation;
import account.operation.OperationCreator;
import account.operation.OperationExecutor;
import command.dto.CommandData;
import command.parser.Parser;

import java.util.List;
import java.util.Scanner;

/**
 * CLI-взаимодействие с пользователем.
 */
public class CommandLineProcessor {
    private final Scanner scanner;

    public CommandLineProcessor(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Создает цикл ввода-вывода.
     */
    public void start() {
        System.out.println("Управление банковскими счетами. Введите команду (или 'exit' для выхода):");
        
        while (true) {
            System.out.print("> ");
            
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Завершение работы");
                break;
            }
            
            if (input.isEmpty()) {
                continue;
            }

            // парсинг команд
            List<CommandData> commandDataList = Parser.parseCommandsFromString(input);
            if (commandDataList.isEmpty()) {
                // перехват синтаксических ошибок
                // Неверный формат команды (deposit, close, transfer).
                //Неправильная последовательность токенов.
                //Некорректный UUID.
                //Некорректная сумма (amount).
                //Пропущенные аргументы.
                System.out.println("err invalid input");
                continue;
            }

            for (CommandData commandData : commandDataList) {
                // создание операции
                Operation operation = OperationCreator.createOperation(commandData);
                // выполнение операции
                OperationExecutor.executeOperation(operation, System.out, System.err);
            }
        }
    }
}
