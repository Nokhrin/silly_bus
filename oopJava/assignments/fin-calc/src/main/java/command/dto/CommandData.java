package command.dto;

/**
 * dto команд.
 */
sealed public interface CommandData permits OpenAccountData, CloseAccountData, ListAccountsData, BalanceData, DepositData, WithdrawData, TransferData {

}
