package CalcParser;
import static CalcParser.Parser.*;
import java.util.Optional;


public class NaryExpression {

    /**
     * Парсер многоместных операций
     * <p>  
     * Синтаксическая грамматика в нотации eBNF:
     *  add_sub_expression ::= mul_div_expression { [ws] add_sub_operation [ws] mul_div_expression }
     *  mul_div_expression ::= num_value { [ws] mul_div_operation [ws] num_value }
     *  mul_div_operation ::= "*" | "/"
     *  add_sub_operation ::= "+" | "-"
     *  num_value ::= [sign] digit {digit}
     *  digit ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
     *  sign ::= "+" | "-"
     *  ws ::= (" " | "\t" | "\n" | "\r") {" " | "\t" | "\n" | "\r"}
     *  
     * @see Parser
     * @param source исходная строка  
     * @param start  стартовый индекс  
     * @return ParseResult(Expression) или Optional.empty(), если парсинг не удался
     */
    public static Optional<ParseResult<Expression>> parseNaryExpression(String source, int start) {
        // проверка входных данных
        if (source.isEmpty() || start < 0 || start >= source.length()) {
            return Optional.empty();
        }
        
        //add_sub_expression ::= mul_div_expression { [ws] add_sub_operation [ws] mul_div_expression }
        //
        Optional<ParseResult<Expression>> mulDivExpressionOpt = parseMulDivExpression(source, start);
        if (mulDivExpressionOpt.isEmpty()) { return Optional.empty(); }
        
        Expression mulDivExpression1 = mulDivExpressionOpt.get().value();
        int offset = mulDivExpressionOpt.get().end();
        
        //{ [ws] add_sub_operation [ws] mul_div_expression }
        while (offset < source.length()) {
            //[ws]
            Optional<ParseResult<String>> ws = parseWhitespace(source, offset);
            if (ws.isPresent()) { offset = ws.get().end(); }
            
            //add_sub_operation
            Optional<ParseResult<Operation>> opOpt = parseAddSubOperation(source, offset);
            if (opOpt.isEmpty()) {
                // оператора нет, выражение { [ws] add_sub_operation [ws] mul_div_expression } невалидно
                break; 
            } 
            Operation op = opOpt.get().value();
            offset = opOpt.get().end();
            
            //[ws]
            ws = parseWhitespace(source, offset);
            if (ws.isPresent()) { offset = ws.get().end(); }
            
            //mul_div_expression
            Optional<ParseResult<Expression>> mulDivExpressionOpt2 = parseMulDivExpression(source, offset);
            if (mulDivExpressionOpt2.isEmpty()) {
                // операции нет, выражение { [ws] add_sub_operation [ws] mul_div_expression } невалидно
                break; 
            }
            
            Expression mulDivExpression2 = mulDivExpressionOpt2.get().value();
            offset = mulDivExpressionOpt2.get().end();

            //add_sub_expression ::= mul_div_expression { [ws] add_sub_operation [ws] mul_div_expression }
            //выполняем add_sub_operation
            //накапливаем итог в первом mul_div_expression
            mulDivExpression1 = new BinaryExpression(mulDivExpression1, op, mulDivExpression2);
        }

        return Optional.of(new ParseResult<>(mulDivExpression1, start, offset));
        
    }
}
