package account.operations;

import account.operations.result.OperationResult;
import command.dto.BalanceData;
import command.dto.CommandData;

import java.util.UUID;

/**
 * Посмотреть баланс счета.
 */
public record Balance(BalanceData balanceData) implements Operation {

    @Override
    public OperationResult execute() {
        
        return null;
    }
}
