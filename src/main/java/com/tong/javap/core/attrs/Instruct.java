package com.tong.javap.core.attrs;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tong on 2017/12/8.
 */
public class Instruct {
    private static final Map<Integer,String> CODE_AND_NAME_MAP = new HashMap<>();

    static {

    }

    private int code;

    public Instruct(int code) {
        this.code = code;
    }

    public static Instruct valueOf(int code) {
        return new Instruct(code);
    }
}
