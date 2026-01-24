package command.dto;

/**
 * dto команды list.
 */
public record ListAccountsData() implements CommandData {
    public static final String COMMAND_TYPE = "list";
}
