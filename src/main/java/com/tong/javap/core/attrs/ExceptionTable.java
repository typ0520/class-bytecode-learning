package com.tong.javap.core.attrs;

import com.tong.javap.core.contant.Constant;
import com.tong.javap.core.utils.ByteCodeStream;
import java.util.List;

/**
 * Created by tong on 2017/12/8.
 */
public class ExceptionTable {
    public ExceptionTable(ByteCodeStream stream, List<Constant> constantList) {
        int start_pc = stream.readUnsignedShort();
        int end_pc = stream.readUnsignedShort();
        int handler_pc = stream.readUnsignedShort();
        int catch_type = stream.readUnsignedShort();
    }
}
