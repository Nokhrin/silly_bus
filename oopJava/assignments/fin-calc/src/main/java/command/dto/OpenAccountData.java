package command.dto;

/**
 * dto команды open.
 */
public record OpenAccountData() implements CommandData {
    public static final String COMMAND_TYPE = "open";
}
