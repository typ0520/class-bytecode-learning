package com.tong.javap.contant;

import com.tong.javap.utils.ByteCodeStream;
import java.util.List;

/**
 * Created by tong on 2017/12/1.
 */
public class LongConstant extends Constant {
    public LongConstant() {
        super(Constant.CONSTANT_Long_info, "Long");
    }

    @Override
    public void preHandle(ByteCodeStream stream) {
        byte[] highBytes = stream.readBytes(4);
        byte[] lowBytes = stream.readBytes(4);

    }

    @Override
    public void postHandle(List<Constant> constantList) {

    }
}