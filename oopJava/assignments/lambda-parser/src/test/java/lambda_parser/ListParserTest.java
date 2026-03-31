package lambda_parser;

import org.testng.annotations.Test;

import java.awt.*;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;

public class ListParserTest {
    @Test(description = "парсинг списка integers, максимум 5 подвыражений, передано 3")
    void testThreeInts() {
        ListParser<Integer> listParser = new ListParser<>(new IntParser(), 0, 5);
        Optional<ParseResult<List<Integer>>> result = listParser.parse("1 2 3", 0);
        assertTrue(result.isPresent());
    }

}
//    @Test(description = "integer {[whitespace] binary_operator [whitespace] integer}")
//    @Test(description = "integer {[whitespace] binary_operator [whitespace] integer}")
//    @Test(description = "integer {[whitespace] binary_operator [whitespace] integer}")
