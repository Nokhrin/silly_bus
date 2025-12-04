package CalcParser;

import java.util.Optional;
import java.util.Set;

public class Parser {

    //region Brackets enum
    /**
     * Скобки - группировка, определение вложенности выражений
     * brackets ::= "(" | ")"
     * @see #parseBrackets
     */
    public enum Brackets {
        OPENING,
        CLOSING
    }
    //endregion

    //region Operation enum
    /**
     * Арифметические операторы
     * operations ::= "+" | "-" | "*" | "/"
     */
    public enum Operation {
        ADD,
        SUB,
        MUL,
        DIV
    }
    //endregion

    //region Whitespace
    /**
     * Пробельные символы
     * ws ::= " " | "\t" | "\n" | "\r"
     */
    private static final Set<Character> WHITESPACES = Set.of(
            ' ', '\t', '\n', '\r'
    );
    
    public static boolean isWhitespace(char ch) {
        return WHITESPACES.contains(ch);
    }
    //endregion

    //region parseSign
    /**
     * Парсинг знака
     * sign ::= "+" | "-"
     */
    public static Optional<ParseResult<Boolean>> parseSign(String source, int start) {
        // стандартная проверка исходной строки и индекса
        if (source.isEmpty() || start < 0 || start >= source.length()) { return Optional.empty(); }

        char ch = source.charAt(start);

        // смещение фиксирую в ParseResult
        return switch (ch) {
            case '+' -> Optional.of(new ParseResult<>(true, start, start + 1));
            case '-' -> Optional.of(new ParseResult<>(false, start, start + 1));
            default -> Optional.empty();
        };
    }
    //endregion

    //region parseInt
    /**
     * Парсинг целого числа
     * int ::= [sign] digit {digit}
     * digit  ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
     */
    public static Optional<ParseResult<NumValue>> parseNumber(String source, int start) {
        // стандартная проверка исходной строки и индекса
        if (source.isEmpty() || start < 0 || start >= source.length()) { return Optional.empty(); }

        int offset = start;
        boolean negative = false;

        // знак с помощью parseSign
        Optional<ParseResult<Boolean>> sign = parseSign(source, offset);
        if (sign.isPresent()) {
            negative = !sign.get().value();
            offset = sign.get().end(); // смещаю курсор на символ знака
        }

        // цифры
        // курсор в пределах строки и после знака идет цифра
        if (offset >= source.length() || !Character.isDigit(source.charAt(offset))) { return Optional.empty(); }

        // читаю цифры
        int num = 0;
        int initialOffset = offset;

        while (offset < source.length() && Character.isDigit(source.charAt(offset))) {
            // `digitAsChar - '0'` - для преобразования в int
            // это выражение - неявно - выполняет игнорирование ведущих нулей
            num = num * 10 + (source.charAt(offset) - '0');
            offset++;
        }

        // цифр нет
        if (offset == initialOffset) { return Optional.empty(); }

        // применяю знак
        if (negative) {
            num = -1 * num;
        }

        return Optional.of(new ParseResult<>(new NumValue(num), start, offset));
    }
    //endregion

    //region parseBrackets
    /**
     * Парсинг скобок
     * bracket ::= "(" | ")"
     */
    public static Optional<ParseResult<Brackets>> parseBrackets(String source, int start) {
        // стандартная проверка исходной строки и индекса
        if (source.isEmpty() || start < 0 || start >= source.length()) { return Optional.empty(); }

        char br = source.charAt(start);

        return switch (br) {
            case '(' -> Optional.of(new ParseResult<>(Brackets.OPENING, start, start + 1));
            case ')' -> Optional.of(new ParseResult<>(Brackets.CLOSING, start, start + 1));
            default -> Optional.empty();
        };
    }
    //endregion
    
    //region parseOperation
    /**
     * Парсинг оператора
     * op ::= "+" | "-" | "*" | "/"
     */
    public static Optional<ParseResult<Operation>> parseOperation(String source, int start) {
        // стандартная проверка исходной строки и индекса
        if (source.isEmpty() || start < 0 || start >= source.length()) { return Optional.empty(); }

        char op = source.charAt(start);
        
        return switch (op) {
            case '*' -> Optional.of(new ParseResult<>(Operation.MUL, start, start + 1));
            case '/' -> Optional.of(new ParseResult<>(Operation.DIV, start, start + 1));
            case '+' -> Optional.of(new ParseResult<>(Operation.ADD, start, start + 1));
            case '-' -> Optional.of(new ParseResult<>(Operation.SUB, start, start + 1));
            default -> Optional.empty();
        };
    }
    //endregion

    //region parseWhitespace
    /**
     * Парсинг одного или более пробелов и/или табуляций
     * ws ::= (" " | "\t" | "\n" | "\r") {" " | "\t" | "\n" | "\r"}
     */
    public static Optional<ParseResult<String>> parseWhitespace(String source, int start) {
        // стандартная проверка исходной строки и индекса
        if (source.isEmpty() || start < 0 || start >= source.length()) { return Optional.empty(); }

        int offset = start;

        while (offset < source.length() && (isWhitespace(source.charAt(offset)))) {
            offset++;
        }
        
        if (offset == start) { return Optional.empty(); }
        
        return Optional.of(new ParseResult<>("", start, offset));
    }
    //endregion

    //region parseMulDivOperation
    /**
     * Парсинг оператора умножения или деления  
     * mul_div_operation ::= "*" | "/"  
     */
    public static Optional<ParseResult<Operation>> parseMulDivOperation(String source, int start) {
        if (source.isEmpty() || start < 0 || start >= source.length()) {
            return Optional.empty();
        }

        char op = source.charAt(start);

        return switch (op) {
            case '*' -> Optional.of(new ParseResult<>(Operation.MUL, start, start + 1));
            case '/' -> Optional.of(new ParseResult<>(Operation.DIV, start, start + 1));
            default -> Optional.empty();
        };
    }
    //endregion

    //region parseAddSubOperation
    /**
     * Парсинг оператора сложения или вычитания  
     * add_sub_operator ::= "+" | "-"  
     */
    public static Optional<ParseResult<Operation>> parseAddSubOperation(String source, int start) {
        if (source.isEmpty() || start < 0 || start >= source.length()) {
            return Optional.empty();
        }

        char op = source.charAt(start);

        return switch (op) {
            case '+' -> Optional.of(new ParseResult<>(Operation.ADD, start, start + 1));
            case '-' -> Optional.of(new ParseResult<>(Operation.SUB, start, start + 1));
            default -> Optional.empty();
        };
    }
    //endregion
    
    //region mulDivExpression

    /**
     * Парсинг выражения умножения/деления
     * <p> 
     * Синтаксическая грамматика в нотации eBNF:
     *  mul_div_expression ::= atom_expression { [ws] mul_div_operation [ws] atom_expression }
     *  atom_expression ::= num_value | '(' [ws] add_sub_operation [ws] ')'
     *  mul_div_operation ::= "*" | "/"
     *  add_sub_operation ::= "+" | "-"
     *  num_value ::= [sign] digit {digit}
     *  digit ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
     *  sign ::= "+" | "-"
     *  ws ::= (" " | "\t" | "\n" | "\r") {" " | "\t" | "\n" | "\r"}
     *  
     * @param source
     * @param start
     * @return
     */
    public static Optional<ParseResult<Expression>> parseMulDivExpression(String source, int start) {
        // Проверка входных данных
        if (source.isEmpty() || start < 0 || start >= source.length()) {
            return Optional.empty();
        }

        //mul_div_expression ::= atom_expression { [ws] mul_div_operation [ws] atom_expression }
        
        //atom_expression
        Optional<ParseResult<AtomExpression>> atom_expressionOpt1 = parseAtom(source, start);
        if (atom_expressionOpt1.isEmpty()) { return Optional.empty(); }
        
        Expression atom_expression1 = atom_expressionOpt1.get().value();
        int offset = atom_expressionOpt1.get().end();
        
        //{ [ws] mul_div_operation [ws] num_value }
        while (offset < source.length()) {
            //[ws]
            Optional<ParseResult<String>> ws = parseWhitespace(source, offset);
            if (ws.isPresent()) {
                offset = ws.get().end();
            }
            
            //mul_div_operation
            Optional<ParseResult<Operation>> opOpt = parseMulDivOperation(source, offset);
            if (opOpt.isEmpty()) { 
                //оператора нет, выражение { [ws] mul_div_operation [ws] num_value } невалидно
                break; 
            }
            Operation op = opOpt.get().value();
            offset = opOpt.get().end();

            //[ws]
            ws = parseWhitespace(source, offset);
            if (ws.isPresent()) {
                offset = ws.get().end();
            }

            //num_value
            Optional<ParseResult<AtomExpression>> atom_expressionOpt2 = parseAtom(source, offset);
            if (atom_expressionOpt2.isEmpty()) { 
                //числа нет, выражение { [ws] mul_div_operation [ws] num_value } невалидно
                break; 
            }
            
            Expression atom_expression2 = atom_expressionOpt2.get().value();
            offset = atom_expressionOpt2.get().end();
            
            //mul_div_expression ::= num_value { [ws] mul_div_operation [ws] num_value }
            //накапливаем итог в первом num_value
            atom_expression1 = new BinaryExpression(atom_expression1, op, atom_expression2);
        }

        return Optional.of(new ParseResult<>(atom_expression1, start, offset));
    }
    //endregion mulDivExpression

    //region atom_expression
    /**
     * Парсинг атомарного выражения
     * <p>
     * Синтаксическая грамматика в нотации eBNF:
     * atom_expression ::= num_value | '(' [ws] add_sub_operation [ws] ')'
     * mul_div_operation ::= "*" | "/"
     * add_sub_operation ::= "+" | "-"
     * num_value ::= [sign] digit {digit}
     * digit ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
     * sign ::= "+" | "-"
     * ws ::= (" " | "\t" | "\n" | "\r") {" " | "\t" | "\n" | "\r"}
     *
     * @param source
     * @param start
     * @return
     */
    private static Optional<ParseResult<AtomExpression>> parseAtom(String source, int start) {
        // Проверка входных данных
        if (source.isEmpty() || start < 0 || start >= source.length()) {
            return Optional.empty();
        }

        //atom_expression ::= num_value | '(' [ws] add_sub_operation [ws] ')'
        
        //num_value
        Optional<ParseResult<NumValue>> numValueOpt1 = parseNumber(source, start);
        if (numValueOpt1.isEmpty()) {
            return Optional.empty();
        }
        Expression numValue = numValueOpt1.get().value();
        int offset = numValueOpt1.get().end();

        // ( [ws] add_sub_operation [ws] )
        // '('
        if (source.charAt(start) != '(') {
            return Optional.empty();
        }
        offset++;

        // [ws]
        Optional<ParseResult<String>> wsOpt = parseWhitespace(source, offset);
        if (wsOpt.isPresent()) {
            offset = wsOpt.get().end();
        }

        // add_sub_operation
        Optional<ParseResult<Operation>> opOpt = parseAddSubOperation(source, offset);
        if (opOpt.isEmpty()) {
            return Optional.empty();
        }
        offset = opOpt.get().end();

        // [ws]
        wsOpt = parseWhitespace(source, offset);
        if (wsOpt.isPresent()) {
            offset = wsOpt.get().end();
        }

        // ')'
        if (offset >= source.length() || source.charAt(offset) != ')') {
            return Optional.empty();
        }
        offset++;

        return Optional.empty();
    }
    //endregion atom_expression
}

