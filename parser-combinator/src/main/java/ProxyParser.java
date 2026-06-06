import java.util.Optional;

/**
 * Парсер-оболочка.
 * Переносит разрешение зависимостей в runtime.
 * @param <T>
 */
public class ProxyParser<T> implements Parser<T>{
    private Parser<T> delegate;

    public ProxyParser() {
    }

    @Override
    public Optional<Parsed<T>> apply(String source, int offset) {
        if (delegate == null) {
            throw new IllegalStateException("Не передан делегируемый парсер");
        }
        return delegate.apply(source, offset);
    }

    public void setDelegate(Parser<T> delegate) {
        this.delegate = delegate;
    }
}
