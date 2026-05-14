import java.util.Optional;
import java.util.function.Function;

@FunctionalInterface
public interface Parser<T> {
    Optional<Parsed<T>> apply(String source, int offset);


    default <R> Parser<Tuple<T, R>> plus(Parser<R> right) {
        return this.flatMap(leftParsed -> {
            // внутри mapper
            //this - результат парсера слева
            //right - парсер справа

            //возврат mapper - парсер кортежа
            return (String source, int offset) -> {
                //правый парсер
                Optional<Parsed<R>> rightParsed = right.apply(source, offset);

                if (rightParsed.isEmpty()) {
                    return Optional.empty();
                }

                return Optional.of(
                        new Parsed<>(
                                new Tuple<>(leftParsed.value(), rightParsed.get().value()),
                                rightParsed.get().offset()
                        ));
            };
        });
    }

    default <R> Parser<R> flatMap(Function<Parsed<T>, Parser<R>> mapper) {
        return (String source, int offset) -> {
            Optional<Parsed<T>> leftResult = this.apply(source, offset);

            if (leftResult.isEmpty()) {
                return Optional.empty();
            }

            Parser<R> nextParser = mapper.apply(leftResult.get());

            return nextParser.apply(source, leftResult.get().offset());
        };
    }

    default <R> Parser<Either<T,R>> or(Parser<R> right){
        return (String source, int offset) ->{
            Optional<Parsed<T>> leftResult = this.apply(source, offset);
            //or => или успех, или неудача левого => вызвать правый
            if (leftResult.isEmpty()){
                Optional<Parsed<R>> rightResult = right.apply(source, offset);
                if (rightResult.isEmpty()) {
                    return Optional.empty();
                }
                return Optional.of(new Parsed<>(
                        Either.right(rightResult.get().value()),
                        rightResult.get().offset()));
            }
            return Optional.of(new Parsed<>(
                    Either.left(leftResult.get().value()),
                    leftResult.get().offset()));
        };
    }
}
