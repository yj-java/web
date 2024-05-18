package org.yj.web.thread.singleton;

/**
 * 懒汉式单例
 */
public class LazySingleton {

    // 私有化构造器
    private LazySingleton() {

    }

    private static LazySingleton lazySingleton;

    // 双重检查锁
    public static LazySingleton getInstance() {
        if (lazySingleton == null) {
            synchronized (LazySingleton.class) {
                if (lazySingleton == null) {
                    lazySingleton = new LazySingleton();
                }
            }
        }
        return lazySingleton;
    }

    // 静态内部类
    private static class SingletonHolder {
        private static final LazySingleton INSTANCE = new LazySingleton();
    }

    public static LazySingleton getInstanceInner() {
        return SingletonHolder.INSTANCE;
    }
}
