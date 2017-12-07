package com.tong.javap.core.contant.level0;

import com.tong.javap.core.contant.Constant;
import com.tong.javap.core.utils.ByteCodeStream;
import java.util.List;

/**
 * Created by tong on 2017/12/1.
 */
public class DoubleConstant extends Constant {
    private double value;

    public DoubleConstant() {
        super(Constant.CONSTANT_Double_info, "Double");
    }

    @Override
    public void preHandle(ByteCodeStream stream) {
        byte[] highBytes = stream.readBytes(4);
        byte[] lowBytes = stream.readBytes(4);

        setReady(true);
    }

    @Override
    public void postHandle(List<Constant> constantList) {

    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "DoubleConstant{" +
                "value=" + value +
                '}';
    }
}