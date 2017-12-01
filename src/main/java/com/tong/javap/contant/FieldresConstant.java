package com.tong.javap.contant;

import com.tong.javap.utils.ByteCodeStream;
import java.util.List;

/**
 * Created by tong on 2017/12/1.
 */
public class FieldresConstant extends Constant {
    public FieldresConstant() {
        super(Constant.CONSTANT_Fieldres_info, "Integer");
    }

    @Override
    public void preHandle(ByteCodeStream stream) {
        int classIndex = stream.readUnsignedShort();
        int nameAndTypeIndex = stream.readUnsignedShort();
    }

    @Override
    public void postHandle(List<Constant> constantList) {

    }
}