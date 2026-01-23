package command;

import account.system.AccountService;
import account.operation.Operation;
import command.parser.Parser;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        AccountService accountService = new AccountService();
        String cmdInput = "open deposit 123e4567-e89b-12d3-a456-426614174000 100.00";
        
        CommandExecutor executor = new CommandExecutor(accountService);
        List<Operation> operations = Parser.parseCommandsFromString(cmdInput);
        operations.forEach(executor::addCommand);
        executor.executeCommands();
    }
}
