package account.operations;


import command.dto.CommandData;
import command.dto.OpenAccountData;

/**
 * Создатель операций.
 */
public class OperationCreator {

    /**
     * Создает операцию, описанную в dto, передает команде значения, вложенные в dto.
     */
    public static Operation createOperation(CommandData commandData) {
        return switch (commandData) {
            case OpenAccountData d -> new OpenAccount(d);
//            case CloseAccountData d -> new CloseAccount(d);
//            case ListAccountsData d -> new ListAccounts(d);
//            case BalanceData d -> new Balance(d);
//            case DepositData d -> new Deposit(d);
//            case WithdrawData d -> new Withdraw(d);
//            case TransferData d -> new Transfer(d);
            default -> throw new IllegalStateException("Unexpected value: " + commandData);
        };
    }
}
