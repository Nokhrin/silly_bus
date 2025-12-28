package command_parser.cli;

import command_parser.Amount;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        // Создаём сумму
        Amount amount = new Amount(new BigDecimal("100.50"));
        System.out.println(amount.perform()); // 100.50

    }
}
