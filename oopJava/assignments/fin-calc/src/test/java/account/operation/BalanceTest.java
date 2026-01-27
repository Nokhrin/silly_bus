package account.operation;

import account.system.Account;
import account.system.AccountService;
import account.system.InMemoryAccountRepository;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.UUID;

import static org.testng.Assert.*;

public class BalanceTest {
    private AccountService accountService;

    @BeforeMethod
    public void setUp() {
        InMemoryAccountRepository repository = new InMemoryAccountRepository();
        accountService = new AccountService(repository);
    }

    @Test(description = "Баланс успешно получен")
    public void getBalance_Success() {
        UUID accountId = accountService.openAccount();
        Operation operation = new Balance(accountId);
        OperationResult operationResult = operation.execute(accountService);
        System.out.println(operationResult);
        // Success[
        //  account=Optional[
        //      account.system.Account@732c2a62
        //      ], 
        //  operationName=Balance, 
        //  operationId=b224d0d2-4983-4172-bacd-c156ed4f7f9c, 
        //  operationDatetime=2026-01-27T17:51:17.018437987
        //  ]
    }
    
    

}