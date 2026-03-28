package lambda_parser;

import java.util.List;

public class CombinedImpl implements Combined {
    private final Integer head;
    private final List<Suffix> tail;

    public CombinedImpl(Integer head, List<Suffix> tail) {
        this.head = head;
        this.tail = tail;
    }

    @Override
    public Integer head() {
        return head;
    }

    @Override
    public List<Suffix> tail() {
        return tail;
    }
}
