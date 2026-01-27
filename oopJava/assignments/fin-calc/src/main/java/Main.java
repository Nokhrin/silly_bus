import account.operation.Operation;
import account.operation.OperationCreator;
import account.operation.OperationExecutor;
import account.system.AccountRepository;
import account.system.AccountService;
import account.system.InMemoryAccountRepository;
import command.dto.CommandData;
import command.parser.Parser;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // файловое хранилище счетов
        AccountRepository accountRepository = new InMemoryAccountRepository();
        
        // создание менеджера счетов
        AccountService accountService = new AccountService(accountRepository);
        
        // парсинг ввода
        String cmdInput = "open " +
                "open " +
//                "list" +
//                "deposit 123e4567-e89b-12d3-a456-426614174000 100.00 " +
//                "deposit 123e4567-e89b-12d3-a456-426614174001 50.00 " +
//                "transfer 123e4567-e89b-12d3-a456-426614174000 123e4567-e89b-12d3-a456-426614174001 25.00 " +
//                "balance 123e4567-e89b-12d3-a456-426614174000 " +
//                "balance 123e4567-e89b-12d3-a456-426614174001 " +
                "list";

        List<CommandData> commandDataList = Parser.parseCommandsFromString(cmdInput);
        
        // создание операций
        List<Operation> operationList = new ArrayList<>();
        for (CommandData commandData : commandDataList) {
            operationList.add(OperationCreator.createOperation(commandData));
        }
        
        // выполнение операций
        for (Operation operation : operationList) {
            OperationExecutor.executeOperation(operation, accountService);
        }
        
        // завершение работы
    }
}
