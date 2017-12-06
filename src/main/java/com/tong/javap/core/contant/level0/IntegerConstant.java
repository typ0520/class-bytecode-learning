package com.tong.javap.core.contant.level0;

import com.tong.javap.core.contant.Constant;
import com.tong.javap.core.utils.ByteCodeStream;
import java.util.List;

/**
 * Created by tong on 2017/12/1.
 */
public class IntegerConstant extends Constant {
    private int value;

    public IntegerConstant() {
        super(Constant.CONSTANT_Integer_info, "Integer");
    }

    @Override
    public void preHandle(ByteCodeStream stream) {
        this.value = stream.readInt();
        setReady(true);
    }

    @Override
    public void postHandle(List<Constant> constantList) {

    }

    public int getValue() {
        return value;
    }
}