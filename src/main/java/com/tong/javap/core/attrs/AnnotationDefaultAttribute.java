package com.tong.javap.core.attrs;

import com.tong.javap.core.contant.Constant;
import com.tong.javap.core.utils.ByteCodeStream;
import java.util.List;

/**
 * Created by tong on 2017/12/6.
 */
public class AnnotationDefaultAttribute extends BaseAnnotationsAttribute {
    public static final String NAME = "AnnotationDefault";

    private ElementAnnotation elementAnnotation;

    @Override
    public void preHandle(ByteCodeStream stream,List<Constant> constantList) {
        elementAnnotation = parseElementValue(stream,constantList);
    }

    @Override
    public void postHandle(List<Constant> constantList) {

    }
}
