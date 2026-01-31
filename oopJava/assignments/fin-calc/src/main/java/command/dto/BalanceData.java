package command.dto;

import java.util.UUID; 

/**
 * dto команды balance.
 */
public record BalanceData(UUID accountId) implements CommandData {
    public static final String COMMAND_TYPE = "balance";
    
    public UUID getAccountId() {
        return accountId;
    }
}
