import java.util.Optional;

public class Parsers {

    public static Parser<Character> characterParser (char expectedChar) {

        return (String source, int offset) -> {
            if (offset >= source.length()) {
                return Optional.empty();
            }

            if (source.charAt(offset) == expectedChar){
                return Optional.of(new Parsed<>(expectedChar, offset + 1));
            }
        return Optional.empty();
        };


    }
}
