package com.tong.javap.contant.level0;

import com.tong.javap.contant.Constant;
import com.tong.javap.utils.ByteCodeStream;
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
}