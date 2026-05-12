import java.util.Optional;

public class Parsers {

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

    public static Parser<Character> digitParser(char expectedInt) {
        return (String source, int offset) -> {
            if (offset >= source.length()) {
                return Optional.empty();
            }

            char parsedChar = source.charAt(offset);
            if (parsedChar == expectedInt && Character.isDigit(parsedChar)) {
                return Optional.of(new Parsed<>(expectedInt, offset + 1));
            }

            return Optional.empty();
        };
    }

    public static Parser<String> stringParser(String pattern) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("строка должна быть не пустой");
        }
        return (String source, int offset) -> {

            int effectiveOffset = offset + pattern.length();
            if (effectiveOffset > source.length()) {
                return Optional.empty();
            }

            if (source.startsWith(pattern)) {
                return Optional.of(new Parsed<>(pattern, effectiveOffset));
            }

            return Optional.empty();
        };
    }

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