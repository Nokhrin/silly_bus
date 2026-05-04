import java.util.Optional;

@FunctionalInterface
public interface Parser<T> {
    Optional<Parsed<T>> apply(String input, int offset);
}
