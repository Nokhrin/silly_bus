import java.util.Optional;

public class Calculator {

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
    private final Parser<Expr> exprParser;

    public Calculator(Parser<Expr> exprParser) {
        this.mul = new ProxyParser<>();
        this.sum = new ProxyParser<>();
        this.prime = new ProxyParser<>();

        // num ::= [digit]
        this.num = Parsers.digitParser().oneOrMore().map(characters -> {
            StringBuilder stringBuilder = new StringBuilder();
            for (Character character : characters) {
                stringBuilder.append(character);
            }
            return new Num(Integer.parseInt(stringBuilder.toString()));
        });

        // ( * | / ) prime
        Parser<Tuple<String, Expr>> mulOp = Parsers
                .characterParser('*')
                .alt(Parsers.characterParser('/'))
                .map(String::valueOf)
                .plus(this.prime);

        // ( + | - ) mul
        Parser<Tuple<String, Expr>> sumOp = Parsers.characterParser('+')
                .alt(Parsers.characterParser('-'))
                .map(String::valueOf)
                .plus(this.mul);

        // ( expr )
        Parser<Expr> parenthesisExpr = Parsers.characterParser('(')
                .skipLeft(this.sum)
                .skipRight(Parsers.characterParser(')'));

        // prime ::= num | '(' expr ')'
        this.prime.setDelegate(this.num.alt(parenthesisExpr));

        // mul ::= prime { ( * | / ) prime }
        this.mul.setDelegate(
                this.prime.flatMap(head ->
                        mulOp.zeroOrMore()
                                .map(tail -> {
                                    Expr result = head.value();
                                    for (var pair : tail) {
                                        result = new BinOp(result, pair.left(), pair.right());
                                    }
                                    return result;
                                }))
        );

        // sum ::= mul { ( + | - ) mul }
        this.sum.setDelegate(this.mul.flatMap(head ->
                sumOp.zeroOrMore().map(tail -> {
                    Expr result = head.value();
                    for (var pair : tail) {
                        result = new BinOp(result, pair.left(), pair.right());
                    }
                    return result;
                })));

        this.exprParser = this.sum.skipRight(Parsers.eof());
    }


    /**
     * Возвращает AST
     */
    public Expr parse(String input) {

        Optional<Parsed<Expr>> parsedOptional = exprParser.apply(input, 0);
        if (parsedOptional.isEmpty()) {
            throw new IllegalArgumentException("Синтаксическая ошибка");
        }
        return parsedOptional.get().value();
    }

    /**
     * Вычисляет узел AST
     */
    private double evaluate(Expr astNode) {
        return switch (astNode) {
            case Num(int value) -> value;
            case BinOp(Expr left, String op, Expr right) -> {
                double leftVal = evaluate(left);
                double rightVal = evaluate(right);
                yield switch (op) {
                    case "+" -> leftVal + rightVal;
                    case "-" -> leftVal - rightVal;
                    case "*" -> leftVal * rightVal;
                    case "/" -> leftVal / rightVal; //todo - проверка rightVal==0
                    default -> throw new IllegalArgumentException("Неизвестный оператор");

                };
            }
        };
    }

    /**
     * Предоставляет интерфейс: парсинг -> вычисление
     */
    public double calculate(String input) {
        return evaluate(parse(input));
    }

}
