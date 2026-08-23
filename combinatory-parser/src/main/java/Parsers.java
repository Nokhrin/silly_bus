import java.util.Optional;
import java.util.Set;

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
     * Парсит пробельные символы
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

    /**
     * Проверяет условие "весь ввод распознан"
     */
    public static Parser<String> eof(){
        return (source, offset) -> {
            if (offset==source.length()) {
                return Optional.of(new Parsed<>("", offset));
            }
            return Optional.empty();
        };
    }

    private static final Set<Character> WHITESPACES = Set.of(' ', '\t', '\n', '\r', '\f');
    /**
     * Поглощает {ws}
     */
    public static Parser<String> whitespaces(){
        return (source, offset) -> {
            int wsOffset = offset;
            while (wsOffset < source.length()
            && WHITESPACES.contains(source.charAt(wsOffset))){
                wsOffset++;
            }
            return Optional.of(new Parsed<>("", wsOffset));
        };
    }
}