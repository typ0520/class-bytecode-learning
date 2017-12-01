package com.tong.javap;

import com.tong.javap.contant.Constant;
import com.tong.javap.utils.ByteCodeStream;
import java.lang.reflect.Modifier;

/**
 * Created by tong on 2017/11/29.
 */
public class MainClass {
    public static void main(String[] args) throws Exception {
        ByteCodeStream stream = new ByteCodeStream(TestClass.class);

        int offset = 0;

        //正确的魔术是0xcafebabe
        long magicNumber = stream.readUnsignedInt();
        offset += 4;
        System.out.println("获取到魔数: " + Long.toHexString(magicNumber));

        //获取版本信息
        int minorVersion = stream.readUnsignedShort();
        offset += 2;
        int majorVersion = stream.readUnsignedShort();
        offset += 2;
        System.out.println("minor version: " + minorVersion);
        System.out.println("major version: " + majorVersion);

        //获取常量池大小
        int constantPoolCount = stream.readUnsignedShort();
        offset += 2;
        System.out.println("常量池大小: " + constantPoolCount);

        //常量池起始点
        int constantPoolOffset = offset;
        int index = 1;
        while ((constantPoolCount - 1) > 0) {
            int tag = stream.readUnsignedByte();
            offset += 1;
            System.out.println("#" + index + " = " + Constant.getContantDesc(tag));
            if (tag == Constant.CONSTANT_Utf8_info) {
                int length = stream.readUnsignedShort();
                offset += 2;
                byte[] bytes = stream.readBytes(length);
                offset += length;

                System.out.println("  读取到了字符串常量: " + new String(bytes));
            }
            else if (tag == Constant.CONSTANT_Integer_info) {
                byte[] bytes = stream.readBytes(4);
                offset += 4;
            }
            else if (tag == Constant.CONSTANT_Float_info) {
                byte[] bytes = stream.readBytes(4);
                offset += 4;
            }
            else if (tag == Constant.CONSTANT_Long_info) {
                byte[] highBytes = stream.readBytes(4);
                offset += 4;

                byte[] lowBytes = stream.readBytes(4);
                offset += 4;
            }
            else if (tag == Constant.CONSTANT_Double_info) {
                byte[] highBytes = stream.readBytes(4);
                offset += 4;

                byte[] lowBytes = stream.readBytes(4);
                offset += 4;
            }
            else if (tag == Constant.CONSTANT_Class_info) {
                int nameIndex = stream.readUnsignedShort();
                offset += 2;
                System.out.println("  nameIndex: " + nameIndex);
            }
            else if (tag == Constant.CONSTANT_String_info) {
                int stringIndex = stream.readUnsignedShort();
                offset += 2;
            }
            else if (tag == Constant.CONSTANT_Fieldres_info) {
                int classIndex = stream.readUnsignedShort();
                offset += 2;
                int nameAndTypeIndex = stream.readUnsignedShort();
                offset += 2;
            }
            else if (tag == Constant.CONSTANT_Methodref_info) {
                int classIndex = stream.readUnsignedShort();
                offset += 2;
                int nameAndTypeIndex = stream.readUnsignedShort();
                offset += 2;
            }
            else if (tag == Constant.CONSTANT_InterfaceMethodref_info) {
                int classIndex = stream.readUnsignedShort();
                offset += 2;
                int nameAndTypeIndex = stream.readUnsignedShort();
                offset += 2;
            }
            else if (tag == Constant.CONSTANT_NameAndType_info) {
                int nameIndex = stream.readUnsignedShort();
                offset += 2;
                int descriptorIndex = stream.readUnsignedShort();
                offset += 2;
            }
            else if (tag == Constant.CONSTANT_MethodHandle_info) {
                int referenceKind = stream.readUnsignedByte();
                offset += 1;
                int referenceIndex = stream.readUnsignedShort();
                offset += 2;
            }
            else if (tag == Constant.CONSTANT_MethodType_info) {
                int descriptorIndex = stream.readUnsignedShort();
                offset += 2;
            }
            else if (tag == Constant.CONSTANT_InvokeDynamic_info) {
                int bootstrapMethodAttrIndex = stream.readUnsignedShort();
                offset += 2;
                int nameAndTypeIndex = stream.readUnsignedShort();
                offset += 2;
            }

            constantPoolCount--;
            index++;
        }

        int accessFlags = stream.readUnsignedShort();
        offset += 2;
        printlnAccessFlags(accessFlags);

        int thisClass = stream.readUnsignedShort();
        offset += 2;
        System.out.println("thisClass: " + thisClass);

        int superClass = stream.readUnsignedShort();
        offset += 2;
        System.out.println("superClass: " + superClass);

        int interfacesCount = stream.readUnsignedShort();
        offset += 2;
        System.out.println("interfacesCount: " + interfacesCount);

        while (interfacesCount > 0) {
            int classInfoIndex = stream.readUnsignedShort();
            offset += 2;
            interfacesCount--;
        }

        int fieldsCount = stream.readUnsignedShort();
        offset += 2;
        System.out.println("fieldsCount: " + fieldsCount);

        while (fieldsCount > 0) {
            int fieldAccessFlags = stream.readUnsignedShort();
            offset += 2;
            printlnAccessFlags(fieldAccessFlags);

            int nameIndex = stream.readUnsignedShort();
            offset += 2;
            System.out.println("  field-nameIndex: " + nameIndex);
            int descriptorIndex = stream.readUnsignedShort();
            offset += 2;
            int attributesCount = stream.readUnsignedShort();
            offset += 2;
            System.out.println("  field-attributesCount: " + attributesCount);

            while (attributesCount > 0) {
                int attributeNameIndex = stream.readUnsignedShort();
                offset += 2;
                long attributeLength = stream.readUnsignedInt();
                offset += 4;

                //跳过字段的附加属性
                offset += attributeLength;

                stream.readBytes((int) attributeLength);

                attributesCount--;
            }
            fieldsCount--;
        }

        int methodsCount = stream.readUnsignedShort();
        offset += 2;
        System.out.println("methodsCount: " + methodsCount);

        while (methodsCount > 0) {
            int methodAccessFlags = stream.readUnsignedShort();
            offset += 2;
            printlnAccessFlags(methodAccessFlags);

            int nameIndex = stream.readUnsignedShort();
            offset += 2;
            System.out.println("  method-nameIndex: " + nameIndex);
            int descriptorIndex = stream.readUnsignedShort();
            offset += 2;
            System.out.println("  method-descriptorIndex: " + descriptorIndex);
            int attributesCount = stream.readUnsignedShort();
            offset += 2;
            System.out.println("  method-attributesCount: " + attributesCount);

            while (attributesCount > 0) {
                int attributeNameIndex = stream.readUnsignedShort();
                offset += 2;

                long attributeLength = stream.readUnsignedInt();
                offset += 4;


                //跳过方法的附加属性
                offset += attributeLength;
                stream.readBytes((int) attributeLength);

                attributesCount--;
            }

            methodsCount--;
        }

        int attributesCount = stream.readUnsignedShort();
        offset += 2;
        System.out.println("class-attributesCount: " + attributesCount);

        while (attributesCount > 0) {
            int attributeNameIndex = stream.readUnsignedShort();
            offset += 2;
            System.out.println("class-attributeNameIndex: " + attributeNameIndex);

            attributesCount--;
        }
    }

    private static void printlnAccessFlags(int accessFlags) {
        StringBuilder sb = new StringBuilder();
        if (Modifier.isPublic(accessFlags)) {
            sb.append("| public");
        }
        if (Modifier.isPrivate(accessFlags)) {
            sb.append("| private");
        }
        if (Modifier.isProtected(accessFlags)) {
            sb.append("| protected");
        }
        if (Modifier.isAbstract(accessFlags)) {
            sb.append("| abstract");
        }
        if (Modifier.isFinal(accessFlags)) {
            sb.append("| final");
        }
        if (Modifier.isStatic(accessFlags)) {
            sb.append("| static");
        }
        if (Modifier.isVolatile(accessFlags)) {
            sb.append("| volatile");
        }

        System.out.println(accessFlags + " " + sb.toString());
        //......
    }
}
