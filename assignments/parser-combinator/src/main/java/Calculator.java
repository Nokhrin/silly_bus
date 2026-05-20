public class Calculator {
    private final Parser<Expr> exprParser;

    /**
     * Число
     * num
     */
    private final Parser<Expr> num;

    /**
     * Атомарное выражение (парсер)
     * num | ( expr )
     */
    private final ProxyParser<Expr> prime;


    /**
     * Парсер произведения/деления
     * mul ::= prime { ( * | / ) } prime
     */
    private final ProxyParser<Expr> mul;

    /**
     * Парсер сложения/вычитания
     * sum ::= mul { ( + | - ) mul }
     */
    private final ProxyParser<Expr> sum;

    public Calculator(Parser<Expr> exprParser) {
        this.exprParser = exprParser;
        this.mul= new ProxyParser<>();
        this.sum= new ProxyParser<>();

        // num ::= [digit]
        this.num = Parsers.digitParser().map(character -> new Num(character-'0'));

        // prime ::= num | '(' expr ')'
        this.prime = new ProxyParser<>();

        // mul ::= prime { ( * | / ) } prime
        Parser<Tuple<String, Expr>> mulOp =
                Parsers.characterParser('*').or(Parsers.characterParser('/'))
                        .map(c -> c.toString())
                        .plus(this.prime);

        // sum ::= mul { ( + | - ) mul }
        Parser<Tuple<String, Expr>> sumOp =
                Parsers.characterParser('+').or(Parsers.characterParser('-'))
                        .map(c -> c.toString())
                        .plus(this.mul);

        // sum AST
        this.sum.setDelegate(this.mul.flatMap(head->
                sumOp.zeroOrMore().map(tail->{
                    Expr result = head.value();
                    for (var pair:tail){
                        result= new BinOp(result, pair.left(), pair.right());
                    }
                    return result;
                })));
    }


    /**
     * Возвращает AST
     */
public Expr parse(String input){
    return null;
}

    /**
     * Обходит AST, вычисляет
     */
//    evaluate(Expr astSource)

    /**
     * Предоставляет интерфейс: вызывает парсер -> evaluate
     */
//    calculate(String input)

}
