package command.parser.cli;

import command.parser.Command;
import command.parser.Parser;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("управление банковскими счетами");
        //
        String input = "open deposit 123e4567-e89b-12d3-a456-426614174000 100.00 withdraw 123e4567-e89b-12d3-a456-426614174000 50.00";
        List<Command> commands = Parser.parseCommandsFromString(input);
        System.out.println(commands);
        // [
        // Open[], 
        // Deposit[accountId=123e4567-e89b-12d3-a456-426614174000, amount=100.00], 
        // Withdraw[accountId=123e4567-e89b-12d3-a456-426614174000, amount=50.00]
        // ]
    }
}