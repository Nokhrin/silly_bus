package command_parser.cli;

import command_parser.Operation;
import command_parser.ParseResult;
import command_parser.Parser;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        String[] commands = {
                "open",
                "deposit 12345 1000.50",
                "withdraw 67890 -500.00",
                "transfer 12345 67890 200.00",
                "balance 12345",
                "list",
                "close 98765"
        };

        for (String cmd : commands) {
            System.out.println("\nВведена команда:\n" + cmd);
            Optional<ParseResult<Operation>> op = Parser.parse(cmd);
            System.out.println("  Операция: " + op.get().value().type());
            System.out.println("  Сумма: " + op.get().value().amount());
            System.out.println("  Отправитель: " + op.get().value().fromId());
            System.out.println("  Получатель: " + op.get().value().toId());
        }
    }
}