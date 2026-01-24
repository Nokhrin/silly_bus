package command;

import account.system.AccountService;
import account.operation.Operation;
import command.dto.CommandData;
import command.dto.CommandFactory;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CommandExecutor {
    private final AccountService accountService;
    private final Queue<Operation> operationQueue = new ConcurrentLinkedQueue<>();

    public CommandExecutor(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Выполнение Одиночной команды.
     */
    public void executeCommand(CommandData commandData) {
        Operation operation = CommandFactory.createOperation(commandData);
        operation.execute(accountService);
    }

    /**
     * Очередь команд.
     */
    public void addCommand(Operation operation) {
        operationQueue.offer(operation);
    }

    /**
     * Выполнение Очереди команд.
     */
    public void executeCommands() {
        Operation operation;
        while ((operation = operationQueue.poll()) != null) {
            operation.execute(accountService);
        }
    }
}
