package com.tong.javap.core.attrs;

import com.tong.javap.core.contant.Constant;
import com.tong.javap.core.utils.ByteCodeStream;
import java.util.List;

/**
 * Created by tong on 2017/12/6.
 */
public class SourceDebugExtensionAttribute extends Attribute {
    public static final String NAME = "SourceDebugExtension";

    @Override
    public void preHandle(ByteCodeStream stream,List<Constant> constantList) {
        long len = length;
        while (len > 0) {
            int debug_extension = stream.readUnsignedByte();
            len--;
        }
    }

    @Override
    public void postHandle(List<Constant> constantList) {

    }
}
