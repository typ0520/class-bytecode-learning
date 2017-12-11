package com.tong.javap.core;

import com.tong.javap.core.attrs.Attribute;
import com.tong.javap.core.attrs.AttributeFactory;
import com.tong.javap.core.contant.Constant;
import com.tong.javap.core.contant.ConstantFactory;
import com.tong.javap.core.contant.level0.Utf8Constant;
import com.tong.javap.core.contant.level1.ClassConstant;
import com.tong.javap.core.utils.ByteCodeStream;
import com.tong.javap.core.utils.LogUtil;
import com.tong.javap.core.utils.Utils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tong on 2017/12/8.
 */
public class ClassFile {

    /**
     * 当用到invokespecial指令时，需要特殊处理的父类方法。
     */
    public static final int ACC_SUPER = 0x0020;
    /**
     * 标识定义的是接口而不是类
     */
    public static final int ACC_INTERFACE = 0x0200;
    /**
     * 标识注解类型
     */
    public static final int ACC_ANNOTATION = 0x2000;
    /**
     * 标识枚举类型
     */
    public static final int ACC_ENUM = 0x4000;
    /**
     * 标识并非Java源码生成的代码
     */
    public static final int ACC_SYNTHETIC = 0x1000;

    /**
     * 魔数
     */
    private long magic;
    /**
     * 次版本号
     */
    private int minorVersion;
    /**
     * 主版本号
     */
    private int majorVersion;
    /**
     * 常量池
     */
    private List<Constant> constants;

    /**
     * 访问标志
     */
    private int accessFlags;

    /**
     * 类索引
     */
    private ClassConstant thisClass;
    /**
     * 父类索引
     */
    private ClassConstant superClass;
    /**
     * 接口表
     */
    private List<ClassConstant> interfaces = new ArrayList<>();

    /**
     * 字段表
     */
    private List<FieldInfo> fieldInfos = new ArrayList<>();
    /**
     * 方法表
     */
    private List<MethodInfo> methodInfos = new ArrayList<>();
    /**
     * 属性表
     */
    private List<Attribute> attributes = new ArrayList<>();

    public ClassFile(ByteCodeStream stream) {
        //正确的魔术是0xcafebabe
        this.magic = stream.readUnsignedInt();
        LogUtil.d("获取到魔数: " + Long.toHexString(magic));

        //获取版本信息
        this.minorVersion = stream.readUnsignedShort();
        this.majorVersion = stream.readUnsignedShort();

        LogUtil.d("minor version: " + minorVersion);
        LogUtil.d("major version: " + majorVersion);

        //获取常量池大小
        int constantPoolCount = stream.readUnsignedShort();
        LogUtil.d("常量池大小: " + constantPoolCount);

        ConstantFactory constantFactory = new ConstantFactory();

        this.constants = constantFactory.parse(constantPoolCount,stream);

        this.accessFlags = stream.readUnsignedShort();
        Utils.printlnAccessFlags(accessFlags);

        int this_class_index = stream.readUnsignedShort();
        thisClass = (ClassConstant) constants.get(this_class_index - 1);
        LogUtil.d("thisClass: " + thisClass.getNameString()) ;

        int super_class_index = stream.readUnsignedShort();
        this.superClass = (ClassConstant) constants.get(super_class_index - 1);
        LogUtil.d("superClass: " + superClass.getNameString());

        int interfacesCount = stream.readUnsignedShort();
        LogUtil.d("interfacesCount: " + interfacesCount);

        while (interfacesCount > 0) {
            int class_info_index = stream.readUnsignedShort();

            ClassConstant class_info = (ClassConstant) constants.get(class_info_index - 1);
            interfaces.add(class_info);

            interfacesCount--;
        }

        int fieldsCount = stream.readUnsignedShort();
        LogUtil.d("fieldsCount: " + fieldsCount);

        while (fieldsCount > 0) {
            int fieldAccessFlags = stream.readUnsignedShort();
            Utils.printlnAccessFlags(fieldAccessFlags);

            int nameIndex = stream.readUnsignedShort();

            Utf8Constant name = (Utf8Constant) constants.get(nameIndex - 1);
            LogUtil.d("  field-name: " + name);
            int descriptorIndex = stream.readUnsignedShort();
            Utf8Constant descriptor = (Utf8Constant) constants.get(descriptorIndex - 1);
            LogUtil.d("  field-descriptor: " + descriptor);
            int attributesCount = stream.readUnsignedShort();

            LogUtil.d("  field-attributesCount: " + attributesCount);

            AttributeFactory fieldAttributeFactory = new AttributeFactory();
            List<Attribute> attributes = fieldAttributeFactory.parse(attributesCount,stream,constants);

            fieldInfos.add(new FieldInfo(accessFlags,name,descriptor,attributes));
            fieldsCount--;
        }

        int methodsCount = stream.readUnsignedShort();
        LogUtil.d("methodsCount: " + methodsCount);

        while (methodsCount > 0) {
            int methodAccessFlags = stream.readUnsignedShort();

            Utils.printlnAccessFlags(methodAccessFlags);

            int nameIndex = stream.readUnsignedShort();

            Utf8Constant name = (Utf8Constant) constants.get(nameIndex - 1);
            LogUtil.d("  method-name: " + name);
            int descriptorIndex = stream.readUnsignedShort();

            Utf8Constant descriptor = (Utf8Constant) constants.get(descriptorIndex - 1);
            LogUtil.d("  method-descriptor: " + descriptor);
            int attributesCount = stream.readUnsignedShort();

            LogUtil.d("  method-attributesCount: " + attributesCount);

            AttributeFactory methodAttributeFactory = new AttributeFactory();
            List<Attribute> attributes = methodAttributeFactory.parse(attributesCount,stream,constants);

            methodInfos.add(new MethodInfo(accessFlags,name,descriptor,attributes));
            methodsCount--;
        }

        int attributesCount = stream.readUnsignedShort();
        LogUtil.d("class-attributesCount: " + attributesCount);

        AttributeFactory attributeFactory = new AttributeFactory();
        this.attributes = attributeFactory.parse(attributesCount,stream,constants);
    }

    public long getMagic() {
        return magic;
    }

    public int getMinorVersion() {
        return minorVersion;
    }

    public int getMajorVersion() {
        return majorVersion;
    }

    public List<Constant> getConstants() {
        return constants;
    }

    public int getAccessFlags() {
        return accessFlags;
    }

    public ClassConstant getThisClass() {
        return thisClass;
    }

    public ClassConstant getSuperClass() {
        return superClass;
    }

    public List<ClassConstant> getInterfaces() {
        return interfaces;
    }

    public List<FieldInfo> getFieldInfos() {
        return fieldInfos;
    }

    public List<MethodInfo> getMethodInfos() {
        return methodInfos;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }
}
