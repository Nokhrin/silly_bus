package command.dto;

sealed public interface CommandData permits OpenAccountData, CloseAccountData, ListAccountsData, BalanceData, DepositData, WithdrawData, TransferData {

}


