package com.tong.javap.core.attrs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tong on 2017/12/7.
 */
public class ParameterAnnotation {
    public int num_annotations;

    public ParameterAnnotation(int num_annotations) {
        this.num_annotations = num_annotations;
    }

    private List<ElementAnnotation> elementAnnotationList = new ArrayList<>();

    public List<ElementAnnotation> getElementAnnotationList() {
        return elementAnnotationList;
    }

}
