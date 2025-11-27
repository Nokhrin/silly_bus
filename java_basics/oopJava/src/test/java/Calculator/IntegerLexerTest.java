    package Calculator;
    
    import org.testng.Assert;
    import org.testng.annotations.Test;
    
    import java.util.Optional;
    
    import static org.testng.Assert.assertEquals;
    
    public class IntegerLexerTest {
    
        private final IntegerLexer lexer = new IntegerLexer();
    
        // Тест: положительное число
        @Test
        public void testParsePositiveNumber() {
            Optional<ParseResult<Integer>> result = lexer.parseFrom("123", 0);
            Assert.assertTrue(result.isPresent());
            assertEquals(result.get().value(), Integer.valueOf("123"));
            assertEquals(result.get().start(), 0);
            assertEquals(result.get().end(), 2);
        }
    
        // Тест: отрицательное число
        @Test
        public void testParseNegativeNumber() {
            Optional<ParseResult<Integer>> result = lexer.parseFrom("-456", 0);
            Assert.assertTrue(result.isPresent());
            assertEquals(result.get().value(), Integer.valueOf("-456"));
            assertEquals(result.get().start(), 0);
            assertEquals(result.get().end(), 3);
        }
    }
