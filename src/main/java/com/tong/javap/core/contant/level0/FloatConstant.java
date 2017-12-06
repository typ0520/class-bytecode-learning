package com.tong.javap.core.contant.level0;

import com.tong.javap.core.contant.Constant;
import com.tong.javap.core.utils.ByteCodeStream;
import java.util.List;

/**
 * Created by tong on 2017/12/1.
 */
public class FloatConstant extends Constant {
    private float value;

    public FloatConstant() {
        super(Constant.CONSTANT_Float_info, "Float");
    }

    @Override
    public void preHandle(ByteCodeStream stream) {
        //byte[] bytes = stream.readBytes(4);
        this.value = Float.intBitsToFloat(stream.readInt());
        setReady(true);
    }

    @Override
    public void postHandle(List<Constant> constantList) {

    }
}