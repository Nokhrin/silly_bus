package lambda_parser;

public class SuffixImpl implements Suffix {
    private final BinaryOperatorParser.Operation operator;
    private final Integer value;

    public SuffixImpl(BinaryOperatorParser.Operation operator, Integer value) {
        this.operator = operator;
        this.value = value;
    }

    @Override
    public BinaryOperatorParser.Operation operator() {
        return operator;
    }

    @Override
    public Integer value() {
        return value;
    }
}
