import fundamentals.HelloWorld;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HelloWorldTest {

    @Test
    public void testWithUsername() {
        HelloWorld hw = new HelloWorld();
        Assert.assertEquals(hw.sayHello("Bob"), "Hello, Bob");
    }

    @Test
    public void testNoUsername() {
        HelloWorld hw = new HelloWorld();
        Assert.assertEquals(hw.sayHello(""), "Hello, ");
    }

}