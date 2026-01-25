package account.operation;

import command.dto.CommandData;
import command.dto.CommandFactory;

public class OperationCreator {
    public static Operation createOperation(CommandData commandData) {
        return CommandFactory.createOperation(commandData);
    }
}
