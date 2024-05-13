package org.yj.web.Java;

public interface InterfaceTest {

    String MESSAGE = "MSG";

    void test();

    default void test(String message) {

    }
}
