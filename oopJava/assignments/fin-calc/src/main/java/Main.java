import account.operation.Operation;
import account.system.AccountService;
import command.CommandExecutor;
import command.dto.CommandData;
import command.dto.CommandFactory;
import command.parser.Parser;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        //1. точка входа
        //2. создание менеджера счетов
        AccountService accountService = new AccountService();
        //3. создание исполнителя команд
        CommandExecutor executor = new CommandExecutor(accountService);
        
        //4. парсинг ввода
        String cmdInput = "open deposit 123e4567-e89b-12d3-a456-426614174000 100.00";
        List<CommandData> commandDataList = Parser.parseCommandsFromString(cmdInput);
        System.out.println(commandDataList);
        // [OpenAccountData[], DepositData[accountId=123e4567-e89b-12d3-a456-426614174000, amount=1E+2]]
        
        //5. создание и выполнение операций
        for (CommandData commandData : commandDataList) {
            executor.executeCommand(commandData);
        }
        //6. завершение работы
    }
}
