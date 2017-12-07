package com.tong.javap.core.attrs;

import com.tong.javap.core.contant.Constant;
import com.tong.javap.core.contant.level0.Utf8Constant;
import com.tong.javap.core.utils.ByteCodeStream;
import com.tong.javap.core.utils.LogUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tong on 2017/12/6.
 */
public abstract class BaseAnnotationsAttribute extends Attribute {
    public static final String NAME = "RuntimeVisibleAnnotations";

    private List<ElementAnnotation> elementAnnotations = new ArrayList<>();

    protected ElementAnnotation parseElementValue(ByteCodeStream stream, List<Constant> constantList) {
        int type_index = stream.readUnsignedShort();
        Utf8Constant type = (Utf8Constant) constantList.get(type_index - 1);
        LogUtil.d(" type: " + type);

        ElementAnnotation elementAnnotation = new ElementAnnotation();
        elementAnnotation.setType(type);

        int num_element_value_pairs = stream.readUnsignedShort();
        while (num_element_value_pairs > 0) {
            int element_name_index = stream.readUnsignedShort();
            Utf8Constant name = (Utf8Constant) constantList.get(element_name_index - 1);

            int tag = stream.readUnsignedByte();

            if (tag == ElementAnnotation.TAG_ENUM) {
                int type_name_index = stream.readUnsignedShort();
                int const_name_index = stream.readUnsignedShort();
            }
            else if (tag == ElementAnnotation.TAG_CLASS) {
                int class_info_index = stream.readUnsignedShort();

            }
            else if (tag == ElementAnnotation.TAG_ANNOTATION_TYPE) {
                //TODO

            }
            else if (tag == ElementAnnotation.TAG_ARRAY) {
                int num_values = stream.readUnsignedShort();

                while (num_values > 0) {
                    //TODO
                    num_values--;
                }
            }
            else {
                int const_value_index = stream.readUnsignedShort();

//                if (tag == ElementAnnotation.TAG_BYTE) {
//
//                }
//                else if (tag == ElementAnnotation.TAG_CHAR) {
//
//                }
//                else if (tag == ElementAnnotation.TAG_DOUBLE) {
//
//                }
//                else if (tag == ElementAnnotation.TAG_FLOAT) {
//
//                }
//                else if (tag == ElementAnnotation.TAG_INT) {
//
//                }
//                else if (tag == ElementAnnotation.TAG_LONG) {
//
//                }
//                else if (tag == ElementAnnotation.TAG_SHORT) {
//
//                }
//                else if (tag == ElementAnnotation.TAG_BOOLEAN) {
//
//                }
//                else if (tag == ElementAnnotation.TAG_STRING) {
//                }

                Constant value =  constantList.get(const_value_index - 1);
                LogUtil.d("  name: " + name + "  value: " + value);
            }

            num_element_value_pairs--;
        }
        return elementAnnotation;
    }

    @Override
    public void postHandle(List<Constant> constantList) {

    }

    public List<ElementAnnotation> getElementAnnotations() {
        return elementAnnotations;
    }
}
