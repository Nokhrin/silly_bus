import java.util.Optional;

public class Parsers {

    /**
     * Парсит точно требуемый символ
     * @param expectedChar
     * @return
     */
    public static Parser<Character> characterParser(char expectedChar) {

        return (String source, int offset) -> {
            if (offset >= source.length()) {
                return Optional.empty();
            }

            if (source.charAt(offset) == expectedChar) {
                return Optional.of(new Parsed<>(expectedChar, offset + 1));
            }
            return Optional.empty();
        };


    }


    public static Parser<String> stringParser(String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            throw new IllegalArgumentException("строка должна быть не пустой");
        }
        return (String source, int offset) -> {

            int effectiveOffset = offset + pattern.length();
            if (effectiveOffset > source.length()) {
                return Optional.empty();
            }

            if (source.startsWith(pattern, offset)) {
                return Optional.of(new Parsed<>(pattern, effectiveOffset));
            }

            return Optional.empty();
        };
    }

    /**
     * Парсит символ класса "цифры"
     * @return
     */
    public static Parser<Character> digitParser() {
        return (String source, int offset) -> {
            if (offset >= source.length()) {
                return Optional.empty();
            }

            char parsedChar = source.charAt(offset);
            if (Character.isDigit(parsedChar)) {
                return Optional.of(new Parsed<>(parsedChar, offset + 1));
            }

            return Optional.empty();
        };
    }

    /**
     * Парсит символ класса "цифры"
     * @return
     */
    public static Parser<Character> whitespaceParser() {
        return (String source, int offset) -> {
            if (offset >= source.length()) {
                return Optional.empty();
            }

            char parsedChar = source.charAt(offset);
            if (Character.isWhitespace(parsedChar)) {
                return Optional.of(new Parsed<>(parsedChar, offset + 1));
            }

            return Optional.empty();
        };
    }

}