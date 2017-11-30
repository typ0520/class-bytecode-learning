package com.example.cbl.contant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tong on 2017/11/30.
 */
public class Constant {
    /**
     * UTF-8编码的字符串
     */
    public static final int CONSTANT_Utf8_info = 1;
    /**
     * 整型字面量
     */
    public static final int CONSTANT_Integer_info = 3;
    /**
     * 浮点型字面量
     */
    public static final int CONSTANT_Float_info = 4;
    /**
     * 长整型字面量
     */
    public static final int CONSTANT_Long_info = 5;
    /**
     * 双精度浮点型字面量
     */
    public static final int CONSTANT_Double_info = 6;
    /**
     * 类或者接口的符号引用
     */
    public static final int CONSTANT_Class_info = 7;
    /**
     * 字符串类型字面量
     */
    public static final int CONSTANT_String_info = 8;
    /**
     * 字段的符号引用
     */
    public static final int CONSTANT_Fieldres_info = 9;
    /**
     * 类中方法的符号引用
     */
    public static final int CONSTANT_Methodref_info = 10;
    /**
     * 接口中方法的符号引用
     */
    public static final int CONSTANT_InterfaceMethodref_info = 11;
    /**
     * 字段或方法的部分符号引用
     */
    public static final int CONSTANT_NameAndType_info = 12;
    /**
     * 表示方法句柄
     */
    public static final int CONSTANT_MethodHandle_info = 15;
    /**
     * 标识方法类型
     */
    public static final int CONSTANT_MethodType_info = 16;
    /**
     * 表示一个动态方法调用点
     */
    public static final int CONSTANT_InvokeDynamic_info = 18;

    private static final Map<Integer,String> CONSTANT_DESC_MAP = new HashMap<Integer,String>();

    static {
        CONSTANT_DESC_MAP.put(CONSTANT_Utf8_info,"Utf8");
        CONSTANT_DESC_MAP.put(CONSTANT_Integer_info,"Integer");
        CONSTANT_DESC_MAP.put(CONSTANT_Float_info,"Float");
        CONSTANT_DESC_MAP.put(CONSTANT_Long_info,"Long");
        CONSTANT_DESC_MAP.put(CONSTANT_Double_info,"Double");
        CONSTANT_DESC_MAP.put(CONSTANT_Class_info,"Class");
        CONSTANT_DESC_MAP.put(CONSTANT_String_info,"String");
        CONSTANT_DESC_MAP.put(CONSTANT_Fieldres_info,"Fieldres");
        CONSTANT_DESC_MAP.put(CONSTANT_Methodref_info,"Methodref");
        CONSTANT_DESC_MAP.put(CONSTANT_InterfaceMethodref_info,"InterfaceMethodref");
        CONSTANT_DESC_MAP.put(CONSTANT_NameAndType_info,"NameAndType");
        CONSTANT_DESC_MAP.put(CONSTANT_MethodHandle_info,"MethodHandle");
        CONSTANT_DESC_MAP.put(CONSTANT_MethodType_info,"MethodType");
        CONSTANT_DESC_MAP.put(CONSTANT_InvokeDynamic_info,"InvokeDynamic");
    }
    
    private final int tag;

    public Constant(int tag) {
        this.tag = tag;
    }

    public int getTag() {
        return tag;
    }

    public String getContantDesc() {
        return getContantDesc(getTag());
    }

    public static String getContantDesc(int tag) {
        return CONSTANT_DESC_MAP.get(tag);
    }
}
