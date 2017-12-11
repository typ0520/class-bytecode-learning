package com.tong.javap.core.contant.level0;

import com.tong.javap.core.contant.Constant;
import com.tong.javap.core.utils.ByteCodeStream;

/**
 * Created by tong on 2017/12/1.
 */
public class DoubleConstant extends ValueConstant<Double> {
    private double value;

    public DoubleConstant() {
        super(Constant.CONSTANT_Double_info, "Double");
    }

    @Override
    public void preHandle(ByteCodeStream stream) {
//        byte[] highBytes = stream.readBytes(4);
//        byte[] lowBytes = stream.readBytes(4);

        this.value = Double.longBitsToDouble(stream.readLong());
        setReady(true);
    }
}