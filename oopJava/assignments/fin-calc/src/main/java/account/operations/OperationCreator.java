package account.operations;


import account.system.AccountRepository;
import command.dto.CommandData;
import command.dto.OpenAccountData;

/**
 * Создатель операций.
 */
public class OperationCreator {
    private final AccountRepository accountRepository;

    public OperationCreator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Создает операцию, описанную в dto, передает команде значения, вложенные в dto.
     */
    public Operation createOperation(CommandData commandData) {
        return switch (commandData) {
            case OpenAccountData d -> new OpenAccount(d, accountRepository);
//            case CloseAccountData d -> new CloseAccount(d, accountRepository);
//            case ListAccountsData d -> new ListAccounts(d, accountRepository);
//            case BalanceData d -> new Balance(d, accountRepository);
//            case DepositData d -> new Deposit(d, accountRepository);
//            case WithdrawData d -> new Withdraw(d, accountRepository);
//            case TransferData d -> new Transfer(d, accountRepository);
            default -> throw new IllegalStateException("Unexpected value: " + commandData);
        };
    }
}
