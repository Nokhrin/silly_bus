package account.system.cli;

import account.operations.Operation;
import account.operations.OperationCreator;
import account.operations.OperationExecutor;
import account.operations.result.FailureResult;
import account.operations.result.OperationResult;
import account.system.AccountRepository;
import command.dto.CommandData;
import command.parser.Parser;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

/**
 * CLI-взаимодействие с пользователем.
 */
public class CommandLineProcessor {
    private final InputSource inputSource;
    private final OperationExecutor operationExecutor;
    private final OperationCreator operationCreator;
    private final ConsoleOutputFormatter consoleOutputFormatter;

    public CommandLineProcessor(InputSource inputSource, AccountRepository accountRepository) {
        this.inputSource = inputSource;
        this.operationExecutor = new OperationExecutor();
        this.operationCreator = new OperationCreator(accountRepository);
        this.consoleOutputFormatter = new ConsoleOutputFormatter();
    }

    /**
     * Создает цикл ввода-вывода.
     */
    public void start() {
        System.out.println("Управление банковскими счетами. Введите команду (или 'exit' для выхода):");

        Scanner scanner = inputSource.getScanner();

        while (true) {
            inputSource.printPrompt();

            if (!scanner.hasNextLine()) {
                // прочитана последняя строка
                break;
            }
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Завершение работы");
                System.out.flush();
                break;
            }

            if (input.isEmpty()) {
                continue;
            }

            // парсинг команд
            List<CommandData> commandDataList = Parser.parseCommandsFromString(input);
            if (commandDataList.isEmpty()) {
                String incorrectInput = consoleOutputFormatter.format(
                        new FailureResult(
                                this.getClass().getSimpleName(),
                                UUID.randomUUID(),
                                LocalDateTime.now(),
                                "некорректный ввод",
                                false
                        ));
                System.out.println(incorrectInput);
                System.out.flush();
                continue;
            }

            for (CommandData commandData : commandDataList) {
                // создание операции
                Operation operation = this.operationCreator.createOperation(commandData);
                // выполнение операции
                OperationResult operationResult = this.operationExecutor.execute(operation);
                // форматирование, вывод
                String formattedOutput = this.consoleOutputFormatter.format(operationResult);

                System.out.println(formattedOutput);
                System.out.flush();
            }
        }
    }
}
