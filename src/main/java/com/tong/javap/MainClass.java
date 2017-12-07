package com.tong.javap;

import com.tong.javap.core.attrs.Attribute;
import com.tong.javap.core.attrs.AttributeFactory;
import com.tong.javap.core.contant.Constant;
import com.tong.javap.core.contant.ConstantFactory;
import com.tong.javap.core.contant.level0.Utf8Constant;
import com.tong.javap.core.contant.level1.ClassConstant;
import com.tong.javap.core.utils.ByteCodeStream;
import com.tong.javap.core.utils.LogUtil;
import com.tong.javap.core.utils.Utils;
import java.util.List;

/**
 * Created by tong on 2017/11/29.
 */
public class MainClass {
    public static void main(String[] args) throws Exception {
        ByteCodeStream stream = new ByteCodeStream(TestClass.class);
        //正确的魔术是0xcafebabe
        long magicNumber = stream.readUnsignedInt();
        LogUtil.d("获取到魔数: " + Long.toHexString(magicNumber));

        //获取版本信息
        int minorVersion = stream.readUnsignedShort();
        int majorVersion = stream.readUnsignedShort();
        
        LogUtil.d("minor version: " + minorVersion);
        LogUtil.d("major version: " + majorVersion);

        //获取常量池大小
        int constantPoolCount = stream.readUnsignedShort();
        LogUtil.d("常量池大小: " + constantPoolCount);

        ConstantFactory constantFactory = new ConstantFactory();

        List<Constant> constantList = constantFactory.parse(constantPoolCount,stream);

        int accessFlags = stream.readUnsignedShort();
        Utils.printlnAccessFlags(accessFlags);

        int thisClass = stream.readUnsignedShort();
        ClassConstant thisClassConstant = (ClassConstant) constantList.get(thisClass - 1);
        LogUtil.d("thisClass: " + thisClassConstant.getNameString()) ;

        int superClass = stream.readUnsignedShort();
        ClassConstant superClassConstant = (ClassConstant) constantList.get(superClass - 1);
        LogUtil.d("superClass: " + superClassConstant.getNameString());

        int interfacesCount = stream.readUnsignedShort();
        LogUtil.d("interfacesCount: " + interfacesCount);

        while (interfacesCount > 0) {
            int classInfoIndex = stream.readUnsignedShort();

            interfacesCount--;
        }

        int fieldsCount = stream.readUnsignedShort();
        LogUtil.d("fieldsCount: " + fieldsCount);

        while (fieldsCount > 0) {
            int fieldAccessFlags = stream.readUnsignedShort();
            Utils.printlnAccessFlags(fieldAccessFlags);

            int nameIndex = stream.readUnsignedShort();

            Utf8Constant name = (Utf8Constant) constantList.get(nameIndex - 1);
            LogUtil.d("  field-name: " + name);
            int descriptorIndex = stream.readUnsignedShort();
            Utf8Constant descriptor = (Utf8Constant) constantList.get(descriptorIndex - 1);
            LogUtil.d("  field-descriptor: " + descriptor);
            int attributesCount = stream.readUnsignedShort();

            LogUtil.d("  field-attributesCount: " + attributesCount);

            AttributeFactory fieldAttributeFactory = new AttributeFactory();
            fieldAttributeFactory.parse(attributesCount,stream,constantList);

//            while (attributesCount > 0) {
//                int attributeNameIndex = stream.readUnsignedShort();
//
//                Utf8Constant attrName = (Utf8Constant) constantList.get(attributeNameIndex - 1);
//                long attributeLength = stream.readUnsignedInt();
//
//                LogUtil.d("  field-attribute: " + constantList.get(attributeNameIndex - 1));
//
//                //跳过字段的附加属性
//
//                stream.readBytes((int) attributeLength);
//
//                attributesCount--;
//            }
            fieldsCount--;
        }

        int methodsCount = stream.readUnsignedShort();
        LogUtil.d("methodsCount: " + methodsCount);

        while (methodsCount > 0) {
            int methodAccessFlags = stream.readUnsignedShort();

            Utils.printlnAccessFlags(methodAccessFlags);

            int nameIndex = stream.readUnsignedShort();

            LogUtil.d("  method-name: " + constantList.get(nameIndex - 1));
            int descriptorIndex = stream.readUnsignedShort();

            LogUtil.d("  method-descriptor: " + constantList.get(descriptorIndex - 1));
            int attributesCount = stream.readUnsignedShort();

            LogUtil.d("  method-attributesCount: " + attributesCount);

            AttributeFactory methodAttributeFactory = new AttributeFactory();
            methodAttributeFactory.parse(attributesCount,stream,constantList);
//            while (attributesCount > 0) {
//                int attributeNameIndex = stream.readUnsignedShort();
//
//                LogUtil.d("  method-attribute: " + constantList.get(attributeNameIndex - 1));
//                long attributeLength = stream.readUnsignedInt();
//                //跳过方法的附加属性
//                stream.readBytes((int) attributeLength);
//
//                attributesCount--;
//            }

            methodsCount--;
        }

        int attributesCount = stream.readUnsignedShort();
        LogUtil.d("class-attributesCount: " + attributesCount);

        AttributeFactory attributeFactory = new AttributeFactory();
        List<Attribute> attributeList = attributeFactory.parse(attributesCount,stream,constantList);
    }
}
