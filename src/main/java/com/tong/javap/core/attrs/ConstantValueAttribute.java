package com.tong.javap.core.attrs;

import com.tong.javap.core.contant.Constant;
import com.tong.javap.core.contant.level0.Utf8Constant;
import com.tong.javap.core.utils.ByteCodeStream;
import com.tong.javap.core.utils.LogUtil;
import java.util.List;

/**
 * Created by tong on 2017/12/6.
 */
public class ConstantValueAttribute extends Attribute {
    public static final String NAME = "ConstantValue";

    private Constant value;

    @Override
    public void preHandle(ByteCodeStream stream,List<Constant> constantList) {
        int constantvalueIndex = stream.readUnsignedShort();
        this.value = constantList.get(constantvalueIndex - 1);

        LogUtil.d("ConstantValue: " + value);
    }

    @Override
    public void postHandle(List<Constant> constantList) {

    }

    public String getValueString() {
        return value.toString();
    }

    @Override
    public String toString() {
        return "ConstantValueAttribute{" +
                "value=" + value +
                '}';
    }
}
