package main.java.org.example.polymorphism;

import java.util.List;

/**
 * динамический полиморфизм
 */
interface Serializable {
    String serialize();
}

// 
class JSONSerializer implements Serializable {
    @Override
    public String serialize() {
        return "JSONSerializer";
    }
};

class ProtoBufSerializer implements Serializable {
    @Override
    public String serialize() {
        return "ProtoBufSerializer";
    }
};


class XMLSerializer implements Serializable {
    @Override
    public String serialize() {
        return "XMLSerializer";
    }
};


public class SerializableDynamic {
    public static void main(String[] args) {
        /**
         * динамический полиморфизм
         * добавление нового типа => создание нового класса
         * => не надо изменять существующие => не надо тестировать заново
         * ключевое - добавить функционал без изменения существующего
         */
        Serializable xmlSerializer = new XMLSerializer();
        Serializable jsonSerializer = new JSONSerializer();
        Serializable protoBufSerializer = new ProtoBufSerializer();

        List<Serializable> serializableList = List.of(xmlSerializer, jsonSerializer, protoBufSerializer);
        for (Serializable serializer : serializableList) {
            serializer.serialize();
        }
    }
}