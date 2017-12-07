package com.tong.javap.core.attrs;

import com.tong.javap.core.contant.Constant;
import com.tong.javap.core.utils.ByteCodeStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tong on 2017/12/6.
 */
public class RuntimeVisibleParameterAnnotationsAttribute extends BaseAnnotationsAttribute {
    public static final String NAME = "RuntimeVisibleParameterAnnotations";

    private List<ParameterAnnotation> parameterAnnotations = new ArrayList<>();

    @Override
    public void preHandle(ByteCodeStream stream,List<Constant> constantList) {
        int num_parameters = stream.readUnsignedByte();
        while (num_parameters > 0) {
            int num_annotations = stream.readUnsignedShort();

            ParameterAnnotation parameterAnnotation = new ParameterAnnotation(num_annotations);
            while (num_annotations > 0) {
                ElementAnnotation elementAnnotation = parseElementValue(stream,constantList);
                parameterAnnotation.getElementAnnotationList().add(elementAnnotation);

                num_annotations--;
            }
            parameterAnnotations.add(parameterAnnotation);
            num_parameters--;
        }
    }
}
