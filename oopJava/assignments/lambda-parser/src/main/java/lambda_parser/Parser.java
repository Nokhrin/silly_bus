package lambda_parser;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

interface Parser<A> {
    /**
     * 
     * @param source
     * @param begin_offset - признак потребления строки
     * @return
     */
    Optional<ParseResult<A>> parse(String source, int begin_offset);

    default <B> Parser<B> map(Function<A, B> function) {
        return null;
    }

    default <B> Parser<B> flatmap(Function<A, Optional<B>> function) {
        return null;
    }

    /**
     * Операция repeat
     * <p>
     * На шаге 4 уже описывался синтаксис {} и [], теперь эти операции должны
     * быть выражены в Parser
     * interface Parser < А> {
     * ...
     * default
     * Parser< List< A> >
     * repeat (int min, int max)
     * default
     * Parser< Optional< А> >
     * optional()
     * }
     */
    default Parser<List<A>> repeat(int min, int max) {
        return null;
    }
}


