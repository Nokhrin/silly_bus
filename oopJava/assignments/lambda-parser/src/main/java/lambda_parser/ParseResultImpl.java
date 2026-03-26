package lambda_parser;

class ParseResultImpl<A> implements ParseResult<A> {
    private final A value;
    private final int end_offset;

    public ParseResultImpl(A value, int end_offset) {
        this.value = value;
        this.end_offset = end_offset;
    }

    @Override
    public A value() {
        return value;
    }

    @Override
    public int end_offset() {
        return end_offset;
    }

}
