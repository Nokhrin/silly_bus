package main.java.lambda_parser;

public interface ParseResult<A> {
    A value();

    int end_offset();
}
