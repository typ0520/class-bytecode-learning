package com.tong.javap.contant;

import com.tong.javap.utils.ByteCodeStream;

import java.util.List;

/**
 * Created by tong on 2017/12/1.
 */
public class StringConstant extends Constant {
    public StringConstant() {
        super(Constant.CONSTANT_String_info, "String");
    }

    @Override
    public void preHandle(ByteCodeStream stream) {
        int stringIndex = stream.readUnsignedShort();

    }

    @Override
    public void postHandle(List<Constant> constantList) {

    }
}