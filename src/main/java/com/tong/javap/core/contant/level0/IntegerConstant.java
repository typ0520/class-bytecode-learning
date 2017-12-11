package com.tong.javap.core.contant.level0;

import com.tong.javap.core.contant.Constant;
import com.tong.javap.core.utils.ByteCodeStream;

/**
 * Created by tong on 2017/12/1.
 */
public class IntegerConstant extends ValueConstant<Integer> {
    public IntegerConstant() {
        super(Constant.CONSTANT_Integer_info, "Integer");
    }

    @Override
    public void preHandle(ByteCodeStream stream) {
        this.value = stream.readInt();
        setReady(true);
    }
}