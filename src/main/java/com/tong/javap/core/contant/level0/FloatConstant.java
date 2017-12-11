package com.tong.javap.core.contant.level0;

import com.tong.javap.core.contant.Constant;
import com.tong.javap.core.utils.ByteCodeStream;

/**
 * Created by tong on 2017/12/1.
 */
public class FloatConstant extends ValueConstant<Float> {
    public FloatConstant() {
        super(Constant.CONSTANT_Float_info, "Float");
    }

    @Override
    public void preHandle(ByteCodeStream stream) {
        this.value = Float.intBitsToFloat(stream.readInt());
        setReady(true);
    }
}