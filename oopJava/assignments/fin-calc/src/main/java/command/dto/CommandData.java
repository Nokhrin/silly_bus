package command.dto;

import account.system.Amount;

import java.util.UUID;

sealed public interface CommandData permits OpenAccountData, CloseAccountData, ListAccountsData, BalanceData, DepositData, WithdrawData, TransferData {

}


