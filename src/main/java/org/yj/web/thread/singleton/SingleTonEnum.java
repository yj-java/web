package org.yj.web.thread.singleton;

public enum SingleTonEnum {
    INSTANCE("张三", 18), INTANCETWO("李四", 19), INTANCETHREE("王二麻子", 20);

    private String title;
    private int age;

    SingleTonEnum(String title, int age) {
        this.title = title;
        this.age = age;
    }

    public void method() {
        System.out.println(this.title);
    }
}
