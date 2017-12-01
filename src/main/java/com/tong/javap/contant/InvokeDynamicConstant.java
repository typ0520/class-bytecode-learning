package com.tong.javap.contant;

import com.tong.javap.utils.ByteCodeStream;
import java.util.List;

/**
 * Created by tong on 2017/12/1.
 */
public class InvokeDynamicConstant extends Constant {
    public InvokeDynamicConstant() {
        super(Constant.CONSTANT_InvokeDynamic_info, "Integer");
    }

    @Override
    public void preHandle(ByteCodeStream stream) {
        int bootstrapMethodAttrIndex = stream.readUnsignedShort();
        int nameAndTypeIndex = stream.readUnsignedShort();
    }

    @Override
    public void postHandle(List<Constant> constantList) {

    }
}