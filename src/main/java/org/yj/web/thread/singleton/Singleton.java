package org.yj.web.thread.singleton;

/**
 * 懒汉式单例
 */
public class Singleton {

    // 私有化构造器
    private Singleton() {

    }

    // 定义静态对象
    private static final Singleton INSTANCE = new Singleton();

    // 暴露访问接口
    public static Singleton getInstance() {
        return INSTANCE;
    }
}
