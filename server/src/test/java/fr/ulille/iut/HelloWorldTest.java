package fr.ulille.iut;

import org.junit.Assert;
import org.junit.Test;

public class HelloWorldTest {
    @Test
    public void test_say_should_return_hello_world() {
        Assert.assertEquals("Hello World!", new HelloWorld().say());
    }
}