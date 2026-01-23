package command;

import account.system.AccountService;
import account.operation.Operation;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CommandExecutor {
    private final AccountService accountService;
    private final Queue<Operation> operationQueue = new ConcurrentLinkedQueue<>();
    
    public CommandExecutor(AccountService accountService) {
        this.accountService = accountService;
    }
    
    public void addCommand(Operation operation) {
        operationQueue.offer(operation); 
    }
    
    public void executeCommands() {
        Operation operation;
        while ((operation = operationQueue.poll()) != null) {
            operation.execute(accountService);
        }
    }
}
