package org.fenixsoft.clazz;

import java.io.Serializable;

/**
 * Created by tong on 2017/11/29.
 */
public final class TestClass implements Serializable,Runnable {
    //public static final long XXX = 100L;

    private static volatile int m;
    private final int m1 = 2;
    private final int m2 = 3;

    public int inc() {
        return m + 1;
    }

    @Override
    public void run() {

    }
}
