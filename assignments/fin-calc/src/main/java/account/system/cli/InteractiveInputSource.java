package account.system.cli;

import java.util.Scanner;

/**
 * Интерактивный ввод в консоли.
 */
public final class InteractiveInputSource implements InputSource {
    @Override
    public Scanner getScanner() {
        return new Scanner(System.in);
    }

    @Override
    public void printPrompt() {
        System.out.print("> ");
        System.out.flush();
    }
}
