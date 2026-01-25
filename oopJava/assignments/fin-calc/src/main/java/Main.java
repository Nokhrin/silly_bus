import account.operation.Operation;
import account.operation.OperationCreator;
import account.operation.OperationExecutor;
import account.system.AccountService;
import command.dto.CommandData;
import command.parser.Parser;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //1. точка входа
        //2. создание менеджера счетов
        AccountService accountService = new AccountService();
        
        //3. парсинг ввода
        String cmdInput = "open deposit 123e4567-e89b-12d3-a456-426614174000 100.00";
        List<CommandData> commandDataList = Parser.parseCommandsFromString(cmdInput);
        
        //4. создание операций
        List<Operation> operationList = new ArrayList<>();
        for (CommandData commandData : commandDataList) {
            operationList.add(OperationCreator.createOperation(commandData));
        }
        
        //5. выполнение операций
        for (Operation operation : operationList) {
            OperationExecutor.executeOperation(operation, accountService);
        }
        
        //6. завершение работы
    }
}
