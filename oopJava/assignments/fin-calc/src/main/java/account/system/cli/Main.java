package account.system.cli;

import account.system.AccountRepository;
import account.system.AccountService;
import account.system.InMemoryAccountRepository;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        AccountRepository accountRepository = new InMemoryAccountRepository();
        AccountService accountService = new AccountService(accountRepository);

        Scanner scanner = new Scanner(System.in);
        CommandLineProcessor commandLineProcessor = new CommandLineProcessor(scanner, accountService);
        
        commandLineProcessor.start();
        
    }
}
