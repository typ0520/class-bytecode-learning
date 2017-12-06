package com.tong.javap.core.attrs;

import com.tong.javap.core.contant.Constant;
import com.tong.javap.core.contant.level1.ClassConstant;
import com.tong.javap.core.utils.ByteCodeStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tong on 2017/12/6.
 */
public class ExceptionsAttribute extends Attribute {
    public static final String NAME = "Exceptions";

    private List<ClassConstant> exceptions;

    @Override
    public void preHandle(ByteCodeStream stream,List<Constant> constantList) {
        int exceptionsCount = stream.readUnsignedShort();

        exceptions = new ArrayList<ClassConstant>(exceptionsCount);
        while (exceptionsCount > 0) {
            int index = stream.readUnsignedShort();
            exceptions.add((ClassConstant) constantList.get(index - 1));
            exceptionsCount--;
        }
    }

    @Override
    public void postHandle(List<Constant> constantList) {

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("| ");
        for (ClassConstant constant : exceptions) {
            sb.append(constant.getNameString() + " | ");
        }
        return sb.toString();
    }
}
