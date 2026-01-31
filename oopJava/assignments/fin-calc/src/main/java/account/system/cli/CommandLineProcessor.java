package account.system.cli;

import account.operations.Operation;
import account.operations.OperationCreator;
import account.operations.OperationExecutor;
import account.operations.result.OperationResult;
import account.system.AccountRepository;
import command.dto.CommandData;
import command.parser.Parser;

import java.util.List;
import java.util.Scanner;

/**
 * CLI-взаимодействие с пользователем.
 */
public class CommandLineProcessor {
    private final Scanner scanner;
    private final OperationExecutor operationExecutor;
    private final OperationCreator operationCreator;

    public CommandLineProcessor(Scanner scanner, AccountRepository accountRepository) {
        this.scanner = scanner;
        this.operationExecutor = new OperationExecutor();
        this.operationCreator = new OperationCreator(accountRepository);
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
                // todo - перехват синтаксических ошибок 
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
                Operation operation = this.operationCreator.createOperation(commandData);
                // выполнение операции
                OperationResult operationResult = this.operationExecutor.execute(operation);
                System.out.println(operationResult.message());
            }
        }
    }
}
