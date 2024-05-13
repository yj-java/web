package org.yj.web.Java;

public abstract class ClassTest {

    String message;

    abstract void test();

    private void test(String message){
        String msg = InterfaceTest.MESSAGE;
    }

}
