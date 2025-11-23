package ParsingDraft;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Читает входную строку посимвольно
 *
 * readLine() возвращает null при достижении EOF
 *
 */
public class Lexer {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;
        System.out.println("\nДля завершения работы нажми Ctrl+D (Unix) или Ctrl+Z (Windows)\nВведи строку");
        System.out.flush();
        while ((line = in.readLine()) != null) {
            System.out.println("прочитано: " + line);
            System.out.println("\nДля завершения работы нажми Ctrl+D (Unix) или Ctrl+Z (Windows)\nВведи строку");
            System.out.flush();
        }
        System.out.println("Конец ввода (EOF) - Завершаю работу");
        System.out.flush();
    }
}
