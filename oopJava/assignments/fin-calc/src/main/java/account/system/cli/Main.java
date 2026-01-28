package account.system.cli;

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
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        AccountRepository accountRepository = new InMemoryAccountRepository();
        AccountService accountService = new AccountService(accountRepository);
        
//        String input = "open list";
//        // парсинг команд
//        List<CommandData> commandDataList = Parser.parseCommandsFromString(input);
//
//        // создание операций
//        List<Operation> operationList = new ArrayList<>();
//        for (CommandData commandData : commandDataList) {
//            operationList.add(OperationCreator.createOperation(commandData));
//        }
//
//        // выполнение операций
//        for (Operation operation : operationList) {
//            OperationExecutor.executeOperation(operation, accountService, System.out, System.err);
//        }
        Scanner scanner = new Scanner(System.in);
        CommandLineProcessor commandLineProcessor = new CommandLineProcessor(scanner, accountService);
        commandLineProcessor.start();
        
        
        
    }
}
