package com.tong.javap.core.attrs;

import com.tong.javap.core.contant.Constant;
import com.tong.javap.core.contant.level0.Utf8Constant;
import com.tong.javap.core.utils.ByteCodeStream;
import com.tong.javap.core.utils.LogUtil;
import java.util.List;

/**
 * Created by tong on 2017/12/6.
 */
public class SourceFileAttribute extends Attribute {
    public static final String NAME = "SourceFile";

    private Utf8Constant sourcefile;

    @Override
    public void preHandle(ByteCodeStream stream,List<Constant> constantList) {
        int sourcefileIndex = stream.readUnsignedShort();
        this.sourcefile = (Utf8Constant) constantList.get(sourcefileIndex - 1);

        LogUtil.d("Sourcefile: " + sourcefile.getValue());
    }

    @Override
    public void postHandle(List<Constant> constantList) {

    }

    public Utf8Constant getSourcefile() {
        return sourcefile;
    }
}
