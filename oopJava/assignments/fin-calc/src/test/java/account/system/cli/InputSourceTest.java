package account.system.cli;

import org.testng.annotations.Test;

import java.util.Scanner;

import static org.testng.Assert.*;

public class InputSourceTest {
    @Test(description = "Проверка, что источник отвечает только за создание сканера")
    public void testInteractiveInputSourceResponsibility() {
        // GIVEN
        InputSource source = new InteractiveInputSource();

        // WHEN
        Scanner scanner = source.getScanner();

        // THEN
        assertNotNull(scanner, "Scanner должен быть создан");
    }



}