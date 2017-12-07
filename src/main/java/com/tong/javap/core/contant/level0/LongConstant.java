package com.tong.javap.core.contant.level0;

import com.tong.javap.core.contant.Constant;
import com.tong.javap.core.utils.ByteCodeStream;
import java.util.List;

/**
 * Created by tong on 2017/12/1.
 */
public class LongConstant extends Constant {
    private long value;

    public LongConstant() {
        super(Constant.CONSTANT_Long_info, "Long");
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

    public long getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "LongConstant{" +
                "value=" + value +
                '}';
    }
}