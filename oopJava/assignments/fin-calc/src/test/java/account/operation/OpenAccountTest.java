package account.operation;

import account.system.AccountService;
import account.system.InMemoryAccountRepository;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class OpenAccountTest {
    private AccountService accountService;

    @BeforeMethod
    public void setUp() {
        InMemoryAccountRepository repository = new InMemoryAccountRepository();
        accountService = new AccountService(repository);
    }

    @Test(description = "Успешно открыт счет")
    public void testExecute_Success() {
        OpenAccount openAccount = new OpenAccount();
        OperationResult result = openAccount.execute(accountService);
        Assert.assertTrue(result.isSuccess());
        Assert.assertTrue(result.account().isPresent());
        Assert.assertNotNull(result.account().get().getId());
    }

}