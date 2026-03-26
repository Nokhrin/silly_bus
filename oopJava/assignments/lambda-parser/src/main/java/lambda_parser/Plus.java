package lambda_parser;

/**
 * Операция sequence
 *    В синтаксисе eBNF есть конструкция когда за одной частю следует другая, пример:
 *    digit letter
 *    Тут после цифры (digit) должна следовать буква (letter)
 *    Допустим есть 2 парсера:
 *    Parser< Digit>
 *    Parser< Letter>
 *    Для digit letter должен существовать Parser< Тuple2< Digit,Letter> >
 *    Необходимо создать операцию + для Parser
 *    interface Parser < А> {
 *    ...
 *    default < В>
 *    Parser< Тuple2< A,B> > plus( Parser< B> p)
 *    }
 * 
 * Skip Left, Right отбрасываем через map значение (например whitespace)
 *
 * @param <A>
 * @param <B>
 */
public interface Plus<A, B> extends Parser<Tuple2<A, B>> {
    Parser<A> skipRight();

    Parser<B> skipLeft();
}
