package com.tong.javap.core.attrs;

import com.tong.javap.core.contant.level0.Utf8Constant;

/**
 * Created by tong on 2017/12/7.
 */
public class ElementAnnotation {
    //代表byte
    public static final char TAG_BYTE = 'B';
    //代表char
    public static final char TAG_CHAR = 'C';
    //代表double
    public static final char TAG_DOUBLE = 'D';
    //代表float
    public static final char TAG_FLOAT = 'F';
    //代表int
    public static final char TAG_INT = 'I';
    //代表long
    public static final char TAG_LONG = 'J';
    //代表short
    public static final char TAG_SHORT = 'S';
    //代表boolean
    public static final char TAG_BOOLEAN = 'Z';

    //代表String
    public static final char TAG_STRING = 's';
    //代表enum constant
    public static final char TAG_ENUM = 'e';
    //代表class
    public static final char TAG_CLASS = 'c';
    //代表annotation type
    public static final char TAG_ANNOTATION_TYPE = '@';
    //代表array
    public static final char TAG_ARRAY = '[';

    private Utf8Constant type;

    public ElementAnnotation() {

    }

    public Utf8Constant getType() {
        return type;
    }

    public void setType(Utf8Constant type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ElementAnnotation{" +
                "type=" + type +
                '}';
    }
}
