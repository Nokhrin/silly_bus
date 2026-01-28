package account.system.cli;

import account.operation.Operation;
import account.operation.OperationCreator;
import account.operation.OperationExecutor;
import account.operation.OperationResult;
import account.system.AccountService;
import command.dto.CommandData;
import command.parser.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * CLI-взаимодействие с пользователем.
 */
public class CommandLineProcessor {
    private final Scanner scanner;
    private final AccountService accountService;

    public CommandLineProcessor(Scanner scanner, AccountService accountService) {
        this.scanner = scanner;
        this.accountService = accountService;
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
                System.out.println("err некорректный ввод");
                continue;
            }

            for (CommandData commandData : commandDataList) {
                // создание операции
                Operation operation = OperationCreator.createOperation(commandData);
                // выполнение операции
                OperationExecutor.executeOperation(operation, accountService, System.out, System.err);
            }
        }
    }
}
