package com.tong.javap.core.contant.level2;

import com.tong.javap.core.contant.Constant;
import com.tong.javap.core.utils.ByteCodeStream;
import java.util.List;

/**
 * Created by tong on 2017/12/1.
 */
public class MethodTypeConstant extends Constant {
    public MethodTypeConstant() {
        super(Constant.CONSTANT_MethodType_info, "MethodType");
    }

    @Override
    public void preHandle(ByteCodeStream stream) {
        int descriptorIndex = stream.readUnsignedShort();

    }

    @Override
    public void postHandle(List<Constant> constantList) {
        setReady(true);
    }
}