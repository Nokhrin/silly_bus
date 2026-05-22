import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class CalculatorTest {

    @Test
    public void testCalculator_SimpleSum(){
        Parser<Expr> num = Parsers.digitParser()
                .map(character -> new Num(character-'0'));

        Parser<Tuple<String, Expr>> opAndNum =
                Parsers.characterParser('+')
                .or(Parsers.characterParser('-'))
                        .map(c->c.toString())
                        .plus(num);

        Parser<Expr> sumParser=num.flatMap(head->
            opAndNum.zeroOrMore().map(tail-> {
                Expr result = head.value();
                for (var pair : tail) {
                    result = new BinOp(result, pair.left(), pair.right());
                }
                return result;
            })
                    );
        Calculator calculator = new Calculator(sumParser.skipRight(Parsers.eof()));

        assertEquals(calculator.calculate("1+2-3"), 0.0);
    }

}