package main.java.lambda_parser;

import java.util.Optional;

interface Parser<A> {
    Optional<ParseResult<A>> parse(String source, int begin_offset);
}