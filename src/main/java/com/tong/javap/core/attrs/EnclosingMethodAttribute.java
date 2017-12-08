package com.tong.javap.core.attrs;

import com.tong.javap.core.contant.Constant;
import com.tong.javap.core.contant.level1.ClassConstant;
import com.tong.javap.core.contant.level1.NameAndTypeConstant;
import com.tong.javap.core.utils.ByteCodeStream;
import com.tong.javap.core.utils.LogUtil;
import java.util.List;

/**
 * Created by tong on 2017/12/6.
 */
public class EnclosingMethodAttribute extends Attribute {
    public static final String NAME = "EnclosingMethod";

    private ClassConstant clazz;
    private NameAndTypeConstant method;

    @Override
    public void preHandle(ByteCodeStream stream,List<Constant> constantList) {
        int class_index = stream.readUnsignedShort();
        clazz = (ClassConstant) constantList.get(class_index - 1);

        int method_index = stream.readUnsignedShort();
        method = (NameAndTypeConstant) constantList.get(method_index - 1);

        LogUtil.d(" method: " + method);
    }

    @Override
    public void postHandle(List<Constant> constantList) {

    }

    public ClassConstant getClazz() {
        return clazz;
    }

    public NameAndTypeConstant getMethod() {
        return method;
    }
}
