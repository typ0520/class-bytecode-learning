package com.tong.javap.contant;

import com.tong.javap.utils.ByteCodeStream;
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
    }

    @Override
    public void postHandle(List<Constant> constantList) {

    }
}