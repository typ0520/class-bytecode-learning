package com.tong.javap.contant;

import com.tong.javap.utils.ByteCodeStream;

import java.util.List;

/**
 * Created by tong on 2017/12/1.
 */
public class NameAndTypeConstant extends Constant {
    public NameAndTypeConstant() {
        super(Constant.CONSTANT_NameAndType_info, "NameAndType");
    }

    @Override
    public void preHandle(ByteCodeStream stream) {
        int nameIndex = stream.readUnsignedShort();
        int descriptorIndex = stream.readUnsignedShort();
    }

    @Override
    public void postHandle(List<Constant> constantList) {

    }
}