package MultipleInheritance.Interfaces;

class DSuper implements DefaultA, DefaultB {

    @Override
    public void method() {
        DefaultA.super.method();
    }
}

