package com.tong.javap.core.attrs;

import com.tong.javap.core.contant.Constant;
import com.tong.javap.core.contant.level0.Utf8Constant;
import com.tong.javap.core.utils.ByteCodeStream;
import com.tong.javap.core.utils.LogUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tong on 2017/12/6.
 */
public class AttributeFactory {
    private static final Map<String,Class> ATTRIBUTE_NAME_MAP = new HashMap<String, Class>();

    static {
        ATTRIBUTE_NAME_MAP.put(ConstantValueAttribute.NAME,ConstantValueAttribute.class);
        ATTRIBUTE_NAME_MAP.put(CodeAttribute.NAME,CodeAttribute.class);
        //StackMap
        ATTRIBUTE_NAME_MAP.put(ExceptionsAttribute.NAME,ExceptionsAttribute.class);
        ATTRIBUTE_NAME_MAP.put(InnerClassesAttribute.NAME,InnerClassesAttribute.class);
        ATTRIBUTE_NAME_MAP.put(EnclosingMethodAttribute.NAME,EnclosingMethodAttribute.class);
        //Synthetic
        ATTRIBUTE_NAME_MAP.put(SignatureAttribute.NAME,SignatureAttribute.class);
        ATTRIBUTE_NAME_MAP.put(SourceFileAttribute.NAME,SourceFileAttribute.class);
        ATTRIBUTE_NAME_MAP.put(SourceDebugExtensionAttribute.NAME,SourceDebugExtensionAttribute.class);
        //LineNumberTable
        //LocalVariableTable
        //LocalVariableTypeTable
        ATTRIBUTE_NAME_MAP.put(DeprecatedAttribute.NAME,DeprecatedAttribute.class);
        ATTRIBUTE_NAME_MAP.put(RuntimeVisibleAnnotationsAttribute.NAME,RuntimeVisibleAnnotationsAttribute.class);
        ATTRIBUTE_NAME_MAP.put(RuntimeInvisibleAnnotationsAttribute.NAME,RuntimeInvisibleAnnotationsAttribute.class);
        ATTRIBUTE_NAME_MAP.put(RuntimeVisibleParameterAnnotationsAttribute.NAME,RuntimeVisibleParameterAnnotationsAttribute.class);
        ATTRIBUTE_NAME_MAP.put(RuntimeInvisibleParameterAnnotationsAttribute.NAME,RuntimeInvisibleParameterAnnotationsAttribute.class);
        ATTRIBUTE_NAME_MAP.put(AnnotationDefaultAttribute.NAME,AnnotationDefaultAttribute.class);
        //BootstrapMethods
    }

    public List<Attribute> parse(int attributesCount, ByteCodeStream stream, List<Constant> constantList) {
        final List<Attribute> attributeList = new ArrayList<Attribute>();

        while (attributesCount > 0) {
            int attributeNameIndex = stream.readUnsignedShort();
            long attributeLength = stream.readUnsignedInt();

            Utf8Constant attrName = (Utf8Constant) constantList.get(attributeNameIndex - 1);
            Attribute attribute = createAttribute(attrName,attributeLength);
            if (attribute == null) {
                stream.readBytes((int) attributeLength);

                LogUtil.d("Skip attributeName: " + constantList.get(attributeNameIndex - 1).toString());
            }
            else {
                attribute.preHandle(stream,constantList);
                LogUtil.d("attributeName: " + attribute.getName());
                LogUtil.d(attribute);

                attributeList.add(attribute);

            }
            attributesCount--;
        }

        return attributeList;
    }

    private Attribute createAttribute(Utf8Constant name, long attributeLength) {
        Class attributeClass = ATTRIBUTE_NAME_MAP.get(name.getValue());
        try {
            Attribute attribute = (Attribute) attributeClass.newInstance();
            attribute.setName(name);
            attribute.setLength(attributeLength);
            return attribute;
        } catch (Throwable e) {
            return null;
        }
    }
}
