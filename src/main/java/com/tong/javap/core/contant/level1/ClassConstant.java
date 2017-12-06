package com.tong.javap.core.contant.level1;

import com.tong.javap.core.contant.Constant;
import com.tong.javap.core.contant.level0.Utf8Constant;
import com.tong.javap.core.utils.ByteCodeStream;
import com.tong.javap.core.utils.LogUtil;
import java.util.List;

/**
 * Created by tong on 2017/12/1.
 */
public class ClassConstant extends Constant {
    private int nameIndex;

    private Utf8Constant name;

    public ClassConstant() {
        super(Constant.CONSTANT_Class_info, "Class");
    }

    @Override
    public void preHandle(ByteCodeStream stream) {
        nameIndex = stream.readUnsignedShort();
    }

    @Override
    public void postHandle(List<Constant> constantList) {
        this.name = (Utf8Constant) constantList.get(nameIndex - 1);

        setReady(true);
        LogUtil.d("  className: " + getNameString());
    }

    public Utf8Constant getName() {
        return name;
    }

    public String getNameString() {
        return getName().getValue();
    }
}