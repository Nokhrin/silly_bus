package MultipleInheritance;

/**
 * правило Java - в одном файле .java - только один public класс
 * классы private, package-private могут быть в этом же файле в любом количестве
 */
class Operation {
    void perform() {
        System.out.println("выполнить ОПЕРАЦИЮ");
    }
}

class Transfer extends Operation {
    @Override
    void perform() {
        System.out.println("выполнить ПЕРЕВОД");
    }
}

public class OperationOverriding {
    public static void main(String[] args) {
        Operation transfer = new Transfer();
        transfer.perform();
    }
}