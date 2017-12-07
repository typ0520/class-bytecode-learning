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
public class RuntimeVisibleAnnotationsAttribute extends Attribute {
    public static final String NAME = "RuntimeVisibleAnnotations";

    private List<ElementValue> elementValues = new ArrayList<>();

    @Override
    public void preHandle(ByteCodeStream stream,List<Constant> constantList) {
        int num_annotations = stream.readUnsignedShort();

        while (num_annotations > 0) {
            ElementValue elementValue = parseElementValue(stream,constantList);
            elementValues.add(elementValue);
            num_annotations--;
        }
    }

    private ElementValue parseElementValue(ByteCodeStream stream, List<Constant> constantList) {
        ElementValue elementValue = new ElementValue();

        int type_index = stream.readUnsignedShort();
        Utf8Constant type = (Utf8Constant) constantList.get(type_index - 1);
        LogUtil.d(" type: " + type);

        int num_element_value_pairs = stream.readUnsignedShort();

        while (num_element_value_pairs > 0) {
            int element_name_index = stream.readUnsignedShort();
            Utf8Constant name = (Utf8Constant) constantList.get(element_name_index - 1);

            int tag = stream.readUnsignedByte();

            if (tag == ElementValue.TAG_ENUM) {
                int type_name_index = stream.readUnsignedShort();
                int const_name_index = stream.readUnsignedShort();
            }
            else if (tag == ElementValue.TAG_CLASS) {
                int class_info_index = stream.readUnsignedShort();

            }
            else if (tag == ElementValue.TAG_ANNOTATION_TYPE) {
                //TODO

            }
            else if (tag == ElementValue.TAG_ARRAY) {
                int num_values = stream.readUnsignedShort();

                while (num_values > 0) {
                    //TODO
                    num_values--;
                }
            }
            else {
                int const_value_index = stream.readUnsignedShort();

//                if (tag == ElementValue.TAG_BYTE) {
//
//                }
//                else if (tag == ElementValue.TAG_CHAR) {
//
//                }
//                else if (tag == ElementValue.TAG_DOUBLE) {
//
//                }
//                else if (tag == ElementValue.TAG_FLOAT) {
//
//                }
//                else if (tag == ElementValue.TAG_INT) {
//
//                }
//                else if (tag == ElementValue.TAG_LONG) {
//
//                }
//                else if (tag == ElementValue.TAG_SHORT) {
//
//                }
//                else if (tag == ElementValue.TAG_BOOLEAN) {
//
//                }
//                else if (tag == ElementValue.TAG_STRING) {
//                }

                Constant value =  constantList.get(const_value_index - 1);
                LogUtil.d("  name: " + name + "  value: " + value);
            }

            num_element_value_pairs--;
        }
        return elementValue;
    }

    @Override
    public void postHandle(List<Constant> constantList) {

    }

    public List<ElementValue> getElementValues() {
        return elementValues;
    }
}
