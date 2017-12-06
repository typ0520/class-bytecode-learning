package com.tong.javap.core.contant.level0;

import com.tong.javap.core.contant.Constant;
import com.tong.javap.core.utils.ByteCodeStream;
import java.util.List;

/**
 * Created by tong on 2017/12/1.
 */
public class Utf8Constant extends Constant {
    private String value;

    public Utf8Constant() {
        super(Constant.CONSTANT_Utf8_info,"Utf8");
    }

    public String getValue() {
        return value;
    }

    @Override
    public void preHandle(ByteCodeStream stream) {
        int length = stream.readUnsignedShort();
        this.value = new String(stream.readBytes(length));

        setReady(true);
        System.out.println("  读取到了字符串常量: " + value);
    }

    @Override
    public void postHandle(List<Constant> constantList) {

    }

    @Override
    public String toString() {
        return this.value;
    }
}
