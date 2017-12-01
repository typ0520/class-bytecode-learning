package com.tong.javap.contant;

import com.tong.javap.utils.ByteCodeStream;
import java.util.List;

/**
 * Created by tong on 2017/12/1.
 */
public class ClassConstant extends Constant {
    public ClassConstant() {
        super(Constant.CONSTANT_Class_info, "Class");
    }

    @Override
    public void preHandle(ByteCodeStream stream) {
        int nameIndex = stream.readUnsignedShort();
        System.out.println("  nameIndex: " + nameIndex);
    }

    @Override
    public void postHandle(List<Constant> constantList) {

    }
}