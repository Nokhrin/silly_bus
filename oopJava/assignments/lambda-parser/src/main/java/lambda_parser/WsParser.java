package lambda_parser;

import java.util.Optional;

public class WsParser implements Parser{
    @Override
    public Optional<ParseResult<String>> parse(String source, int begin_offset) {
        return Optional.empty();
    }
}
