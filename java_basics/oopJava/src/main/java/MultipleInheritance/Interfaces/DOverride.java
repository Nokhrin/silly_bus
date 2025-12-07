package MultipleInheritance.Interfaces;

class DOverride implements DefaultA, DefaultB {

    @Override
    public void method() {
        System.out.println("переопеределенный метод");
    }
}
