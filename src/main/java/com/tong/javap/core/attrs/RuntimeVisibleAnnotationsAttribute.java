package com.tong.javap.core.attrs;

import com.tong.javap.core.contant.Constant;
import com.tong.javap.core.contant.level0.Utf8Constant;
import com.tong.javap.core.utils.ByteCodeStream;
import com.tong.javap.core.utils.LogUtil;

import java.util.List;

/**
 * Created by tong on 2017/12/6.
 */
public class RuntimeVisibleAnnotationsAttribute extends Attribute {
    public static final String NAME = "RuntimeVisibleAnnotations";


    @Override
    public void preHandle(ByteCodeStream stream,List<Constant> constantList) {
        int num_annotations = stream.readUnsignedShort();

        while (num_annotations > 0) {
            int type_index = stream.readUnsignedShort();
            Utf8Constant type = (Utf8Constant) constantList.get(type_index - 1);
            LogUtil.d(" type: " + type);
            num_annotations--;
        }
    }

    @Override
    public void postHandle(List<Constant> constantList) {

    }
}
