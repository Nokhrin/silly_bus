package Calculator;

/**
 * Объект результата парсинга
 */
public class ParseResult {
    private final Integer value;
    private final int start;
    private final int end;

    public ParseResult(Integer value, int start, int end) {
        this.value = value;
        this.start = start;
        this.end = end;
    }

    public Integer getValue() {
        return value;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "ParseResult{" +
                "value=" + value +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
    
    public boolean isSuccess() {
        return value != null;
    }
}
