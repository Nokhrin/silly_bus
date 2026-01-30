package account.operation;

import account.system.Account;
import account.system.AccountRepository;

import java.util.UUID;

/**
 * Открыть счет.
 */
public record OpenAccount() implements Operation {

    @Override
    public OperationResult execute() {
        return null;
    }
}
