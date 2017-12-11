package com.tong.javap.core.contant.level0;

import com.tong.javap.core.contant.Constant;
import com.tong.javap.core.utils.ByteCodeStream;
import java.util.List;

/**
 * Created by tong on 2017/12/1.
 */
public class ValueConstant<T> extends Constant {
    protected T value;

    public ValueConstant(int tag, String desc) {
        super(tag, desc);
    }

    @Override
    public void preHandle(ByteCodeStream stream) {

    }

    @Override
    public void postHandle(List<Constant> constantList) {

    }

    public T getValue() {
        return value;
    }


    @Override
    public String toString() {
        return String.valueOf(this.value);
    }
}