package account.system.cli;

import java.util.Scanner;

/**
 * Источник ввода.
 * 
 */
public sealed interface InputSource permits InteractiveInputSource, FileInputSource {
    /**
     * Возвращает сканер.
     * @return
     */
    Scanner getScanner();
}
