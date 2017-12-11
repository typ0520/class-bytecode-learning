package com.tong.javap.core.contant.level1;

import com.tong.javap.core.contant.Constant;
import com.tong.javap.core.contant.level0.Utf8Constant;
import com.tong.javap.core.utils.ByteCodeStream;
import java.util.List;

/**
 * Created by tong on 2017/12/1.
 */
public class StringConstant extends Constant {
    private int stringIndex;

    private Utf8Constant value;

    public StringConstant() {
        super(Constant.CONSTANT_String_info, "String");
    }

    @Override
    public void preHandle(ByteCodeStream stream) {
        stringIndex = stream.readUnsignedShort();
    }

    @Override
    public void postHandle(List<Constant> constantList) {
        this.value = (Utf8Constant) constantList.get(stringIndex - 1);
        setReady(true);
    }

    public int getStringIndex() {
        return stringIndex;
    }

    public Utf8Constant getValue() {
        return value;
    }

    public String getValueString() {
        return getValue().toString();
    }
}