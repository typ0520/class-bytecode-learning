package com.tong.javap.core.contant.level0;

import com.tong.javap.core.contant.Constant;
import com.tong.javap.core.utils.ByteCodeStream;

/**
 * Created by tong on 2017/12/1.
 */
public class LongConstant extends ValueConstant<Long> {
    public LongConstant() {
        super(Constant.CONSTANT_Long_info, "Long");
    }

    @Override
    public void preHandle(ByteCodeStream stream) {
        this.value = stream.readLong();

        setReady(true);
    }
}