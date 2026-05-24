import java.util.List;
import java.util.Optional;

public class Calculator {
    private final Parser<Expr> exprParser;

    public Calculator() {
        this.exprParser = buildGrammar();
    }

    /**
     * Создает парсер грамматики калькулятора
     * expr  ::= sum
     * sum   ::= mul { ('+' | '-') mul }
     * mul   ::= prime { ('*' | '/') prime }
     * prime ::= num | '(' sum ')'
     * num   ::= [digit]
     * digit ::= '0' | '1' | '2' | '3' | '4' | '5' | '6' | '7' | '8' | '9'
     */
    private Parser<Expr> buildGrammar() {
        ProxyParser<Expr> mul = new ProxyParser<>();
        ProxyParser<Expr> sum = new ProxyParser<>();
        ProxyParser<Expr> prime = new ProxyParser<>();

        // атомарные токены с игнором пробелов
        Parser<Character> leftPar = Parsers.characterParser('(').lexeme();
        Parser<Character> rightPar = Parsers.characterParser(')').lexeme();
        Parser<Character> addSign = Parsers.characterParser('+').lexeme();
        Parser<Character> subSign = Parsers.characterParser('-').lexeme();
        Parser<Character> mulSign = Parsers.characterParser('*').lexeme();
        Parser<Character> divSign = Parsers.characterParser('/').lexeme();

        // num ::= [digit]
        Parser<Expr> num = Parsers.digitParser()
                .oneOrMore()
                .map(characters -> {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (Character character : characters) {
                        stringBuilder.append(character);
                    }
                    return new Num(Integer.parseInt(stringBuilder.toString()));
                });

        // prime ::= num | '(' sum ')'
        // '(' sum ')'
        Parser<Expr> parSum = leftPar.skipLeft(sum).skipRight(rightPar);
        // prime ::= num | '(' sum ')'
        prime.setDelegate(num.alt(parSum));

        // mul   ::= prime { ('*' | '/') prime }
        // { ('*' | '/') prime }
        Parser<List<Tuple<Character, Expr>>> mulTail = mulSign
                .alt(divSign)
                .plus(prime)
                .zeroOrMore();
        // mul   ::= prime { ('*' | '/') prime }
        mul.setDelegate(prime.flatMap(head->
                mulTail.map(tail->{
                    Expr astNode = head.value();
                    for (Tuple<Character, Expr> pair:tail){
                        astNode=new BinOp(astNode, pair.left(), pair.right());
                    }
                    return astNode;
                })));

        // sum   ::= mul { ('+' | '-') mul }
        // { ('+' | '-') mul }
        Parser<List<Tuple<Character, Expr>>> sumTail = addSign
                .alt(subSign)
                .plus(mul)
                .zeroOrMore();
        // mul { ('+' | '-') mul }
        sum.setDelegate(mul.flatMap(head->
                sumTail.map(tail->{
                    Expr astNode = head.value();
                    for (Tuple<Character, Expr> pair:tail){
                        astNode=new BinOp(astNode, pair.left(), pair.right());
                    }
                    return astNode;
                })));

        // expr  ::= sum
        return sum.skipRight(Parsers.eof());
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
            case BinOp(Expr left, char op, Expr right) -> {
                double leftVal = evaluate(left);
                double rightVal = evaluate(right);
                yield switch (op) {
                    case '+' -> leftVal + rightVal;
                    case '-' -> leftVal - rightVal;
                    case '*' -> leftVal * rightVal;
                    case '/' -> {
                        if (rightVal == 0) throw new ArithmeticException("Делитель равен нулю");
                        yield leftVal / rightVal;
                    }
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
