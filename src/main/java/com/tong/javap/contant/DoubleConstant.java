package com.tong.javap.contant;

import com.tong.javap.utils.ByteCodeStream;
import java.util.List;

/**
 * Created by tong on 2017/12/1.
 */
public class DoubleConstant extends Constant {
    public DoubleConstant() {
        super(Constant.CONSTANT_Double_info, "Double");
    }

    @Override
    public void preHandle(ByteCodeStream stream) {

    }

    @Override
    public void postHandle(List<Constant> constantList) {

    }
}