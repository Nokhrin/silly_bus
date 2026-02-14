package main.java.org.example.polymorphism.serializables;


/**
 * Воспроизведение полиморфизма
 */
class Serializer {
    private final String type;

    public Serializer(String type) {
        this.type = type;
    }

    // поведение
    // при изменении требований к функционалу - править здесь
    private String serializeJson() {
        return "JSONSerializer";
    }

    private String serializeProto() {
        return "ProtoBufSerializer";
    }

    private String serializeXml() {
        return "XMLSerializer";
    }

    // при изменении требований к функционалу - править здесь
    public String serialize() {
        return switch (this.type.toLowerCase()) {
            case "json" -> serializeJson();
            case "proto" -> serializeProto();
            case "xml" -> serializeXml();
            default -> throw new IllegalArgumentException("неизвестный сериализатор");
        };
    }

}


public class SerializableStatic {
    public static void main(String[] args) {
        Serializer serializer = new Serializer("proto");
        System.out.println(serializer.serialize());
    }
}