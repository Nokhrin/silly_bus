package lambda_parser;

public interface Suffix {
    BinaryOperatorParser.Operation operator();
    Integer value();
}
