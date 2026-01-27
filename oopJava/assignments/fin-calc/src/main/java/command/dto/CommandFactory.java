package command.dto;


import account.operation.*;

/**
 * Создатель операций.
 */
public class CommandFactory {

    /**
     * Создает операцию, описанную в dto, передает команде значения, вложенные в dto.
     */
    public static Operation createOperation(CommandData commandData) {
        return switch (commandData) {
            case OpenAccountData d -> new OpenAccount();
            case CloseAccountData d -> new CloseAccount(d.accountId());
            case ListAccountsData d -> new ListAccounts();
            case BalanceData d -> new Balance(d.accountId());
            case DepositData d -> new Deposit(d.accountId(), d.toAmount());
            case WithdrawData d -> new Withdraw(d.accountId(), d.toAmount());
            case TransferData d -> new Transfer(d.sourceAccountId(), d.targetAccountId(), d.toAmount());
        };
    }
}
