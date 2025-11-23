package FinOps;

import java.util.ArrayList;
import java.util.List;

/**
 * Поддерживаемые лексемы
 */
enum TokenType {
    OPERATION,    // deposit, withdraw, transfer
    AMOUNT,       // число с 1-2 знаками после точки
    ACCOUNT_ID,   // UUID
    EOF           // конец ввода
}

/**
 * Лексема
 */
class Token {
    TokenType type;
    String value;

    // упрощение -  IntelliJ IDEA → Generate → Constructor
    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    // toString определен в Object - суперклассе всех классов в Java
    // для соблюдения соглашения и проверки при компиляции переопределяю

    // упрощение -  IntelliJ IDEA → Generate → toString()
    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}

/**
 * Возвращает список лексем
 */
public class FinLexer {
    private String input;
    private int offset;
    private char currentChar;

    public FinLexer(String input) {
        this.input = input;
        this.offset = 0;
        // в Java null в тексте принято обозначать конец строки символом с кодом 0 - \u0000
        // \0 обозначает, что на этой позиции подразумевается null, то есть, конец строки в настоящем примере
        this.currentChar = input.isEmpty() ? '\0' : input.charAt(0);
    }

    // переход к следующему символу
    private void moveToNextChar() {
        if (offset < input.length()) {
            currentChar = input.charAt(offset++);
        } else {
            currentChar = '\0';
        }
    }

    // чтение имени операции
    private String readOperation() {
        StringBuilder sb = new StringBuilder();
        while (currentChar != '\0' &&
                (Character.isLetterOrDigit(currentChar))) {
            sb.append(currentChar);
            moveToNextChar();
        }
        return sb.toString();
    }

    // чтение суммы
    private String readAmount() {
        StringBuilder sb = new StringBuilder();
        boolean hasDot = false;
        while (currentChar != '\0') {
            if (Character.isDigit(currentChar)) {
                sb.append(currentChar);
                moveToNextChar();
            } else if (currentChar == '.' && !hasDot) {
                // если символ == точка и точка встречается впервые
                sb.append(currentChar);
                hasDot = true;
                moveToNextChar();
            } else {
                break;
            }
        }

        String amount = sb.toString();
        // сумма не введена
        if (amount.isEmpty()) { throw new IllegalArgumentException("Сумма не введена"); }

        // дробная часть невалидна
        if (hasDot) {
            int dotIndex = amount.indexOf('.');
            // нет цифра после точки или одна цифра после точки
            if (dotIndex == amount.length() - 1 || dotIndex == amount.length() - 2) {
                throw new IllegalArgumentException("Сумма некорректна");
            }
            // более двух цифр после точки
            String fractional = amount.substring(dotIndex + 1);
            if (fractional.length() > 2) { throw new IllegalArgumentException("В дробной части более двух цифр"); }
        }

        return amount;
    }

    // чтение id счета
    private String readId() {
        StringBuilder sb = new StringBuilder();
        while (currentChar != '\0' &&
                (Character.isLetterOrDigit(currentChar) || currentChar == '-')
        ) {
            sb.append(currentChar);
            moveToNextChar();
        }
        return sb.toString();
    }

    // может ли быть началом UUID
    private boolean isPossibleUuidStart(char c) {
        // первый символ в допустимом множестве
        return (c >= '0' && c <= '9') ||
                (c >= 'a' && c <= 'f') ||
                (c == '7' || c == '8' || c == '9' || c == 'b' || c == 'f');
    }

    // извлечение лексем
    public List<Token> getTokens() {
        List<Token> tokens = new ArrayList<>();

        // читать до окончания строки
        while (currentChar != '\0') {
            // игнорировать пробелы - только увеличить смещение
            if (Character.isWhitespace(currentChar)) {
                moveToNextChar();
                continue;
            }

            // распознавание - последовательная проверка на сооответствие предусмотренному типу лексемы
            // операция
            if (Character.isLetter(currentChar)) {
                String op = readOperation();
                tokens.add(new Token(TokenType.OPERATION, op.toUpperCase()));
            }

            // сумма
            if (Character.isDigit(currentChar)) {
                String amount = readAmount();
                tokens.add(new Token(TokenType.AMOUNT, amount));
                continue;
            }

            // id счета
            if (isPossibleUuidStart(currentChar)) {
                String id = readId();
                tokens.add(new Token(TokenType.ACCOUNT_ID, id));
                continue;
            }
        }
        tokens.add(new Token(TokenType.EOF, ""));
        return tokens;
    }


    public static void main(String[] args) {
        List<String> testCases = List.of(
                "deposit 100.50 7317ba2f-9145-4f31-9efe-5b4919b2b027",
                "withdraw 50.75 7317ba2f-9145-4f31-9efe-5b4919b2b027",
                "transfer 200.00 7317ba2f-9145-4f31-9efe-5b4919b2b027 7317ba2f-9145-4f31-9efe-5b4919b2b028",
                "deposit 12.5 123e4567-1234-5678-9abc-123456789abc",
                "deposit 100 123e4567-1234-5678-9abc-123456789abc"
        );

        for (String input : testCases) {
            System.out.println("\nВвод: " + input);
            try {
                FinLexer lexer = new FinLexer(input);
                List<Token> tokens = lexer.getTokens();
                for (Token token : tokens) {
                    System.out.println("  " + token);
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }

        /*
        Вывод:
        Ввод: deposit 100.50 7317ba2f-9145-4f31-9efe-5b4919b2b027
         */

        // TODO
        //  определяет первую команду и зависает
    }
}
