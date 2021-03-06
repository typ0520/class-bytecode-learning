package com.tong.javap.core.contant.level2;

import com.tong.javap.core.contant.Constant;
import com.tong.javap.core.utils.ByteCodeStream;

import java.util.List;

/**
 * Created by tong on 2017/12/1.
 */
public class MethodHandleConstant extends Constant {
    public MethodHandleConstant() {
        super(Constant.CONSTANT_MethodHandle_info, "MethodHandle");
    }

    @Override
    public void preHandle(ByteCodeStream stream) {
        int referenceKind = stream.readUnsignedByte();
        int referenceIndex = stream.readUnsignedShort();
    }

    @Override
    public void postHandle(List<Constant> constantList) {
        setReady(true);
    }
}