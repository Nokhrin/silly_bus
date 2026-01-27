package account.system.cli;

import account.operation.Operation;
import account.operation.OperationCreator;
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

            // создание команд
            List<CommandData> commandDataList = Parser.parseCommandsFromString(input);
            if (commandDataList.isEmpty()) {
                System.out.println("err некорректный ввод");
                continue;
            }

            // создание операций
            List<Operation> operationList = new ArrayList<>();
            for (CommandData commandData : commandDataList) {
                operationList.add(OperationCreator.createOperation(commandData));
            }

            // выполнение операций
            for (Operation operation : operationList) {
                OperationResult operationResult = operation.execute(accountService);
                if (operationResult.isSuccess()){
                    System.out.println("ok");
                } else {
                    System.err.println("err " + operationResult.message());
                }
            }

            // завершение работы
        }
    }
}
