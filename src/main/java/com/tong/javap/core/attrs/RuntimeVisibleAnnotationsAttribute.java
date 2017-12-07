package com.tong.javap.core.attrs;

import com.tong.javap.core.contant.Constant;
import com.tong.javap.core.utils.ByteCodeStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tong on 2017/12/6.
 */
public class RuntimeVisibleAnnotationsAttribute extends BaseAnnotationsAttribute {
    public static final String NAME = "RuntimeVisibleAnnotations";

    private List<ElementAnnotation> elementAnnotations = new ArrayList<>();

    @Override
    public void preHandle(ByteCodeStream stream,List<Constant> constantList) {
        int num_annotations = stream.readUnsignedShort();
        while (num_annotations > 0) {
            ElementAnnotation elementAnnotation = parseElementValue(stream,constantList);
            elementAnnotations.add(elementAnnotation);
            num_annotations--;
        }
    }

    public List<ElementAnnotation> getElementAnnotations() {
        return elementAnnotations;
    }
}
