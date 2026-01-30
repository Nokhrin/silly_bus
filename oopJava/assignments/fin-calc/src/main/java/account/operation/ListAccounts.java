package account.operation;

import account.system.Account;
import account.system.AccountRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Вывести список всех открытых счетов.
 */
public record ListAccounts() implements Operation {

    @Override
    public OperationResult execute() {
        return null;
    }
}
