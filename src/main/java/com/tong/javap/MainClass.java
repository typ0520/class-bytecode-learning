package com.tong.javap;

import com.tong.javap.contant.Constant;
import com.tong.javap.contant.ConstantFactory;
import com.tong.javap.utils.ByteCodeStream;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 * Created by tong on 2017/11/29.
 */
public class MainClass {
    public static void main(String[] args) throws Exception {
        ByteCodeStream stream = new ByteCodeStream(TestClass.class);
        //正确的魔术是0xcafebabe
        long magicNumber = stream.readUnsignedInt();
        System.out.println("获取到魔数: " + Long.toHexString(magicNumber));

        //获取版本信息
        int minorVersion = stream.readUnsignedShort();
        int majorVersion = stream.readUnsignedShort();
        
        System.out.println("minor version: " + minorVersion);
        System.out.println("major version: " + majorVersion);

        //获取常量池大小
        int constantPoolCount = stream.readUnsignedShort();
        System.out.println("常量池大小: " + constantPoolCount);

        ConstantFactory constantFactory = new ConstantFactory();

        List<Constant> constantList = constantFactory.parse(constantPoolCount,stream);

//        int index = 1;
//        while ((constantPoolCount - 1) > 0) {
//            int tag = stream.readUnsignedByte();
//            System.out.println("#" + index + " = " + Constant.getDesc(tag));
//            if (tag == Constant.CONSTANT_Utf8_info) {
//                int length = stream.readUnsignedShort();
//                byte[] bytes = stream.readBytes(length);
//
//                System.out.println("  读取到了字符串常量: " + new String(bytes));
//            }
//            else if (tag == Constant.CONSTANT_Integer_info) {
//                byte[] bytes = stream.readBytes(4);
//            }
//            else if (tag == Constant.CONSTANT_Float_info) {
//                byte[] bytes = stream.readBytes(4);
//            }
//            else if (tag == Constant.CONSTANT_Long_info) {
//                byte[] highBytes = stream.readBytes(4);
//
//                byte[] lowBytes = stream.readBytes(4);
//            }
//            else if (tag == Constant.CONSTANT_Double_info) {
//                byte[] highBytes = stream.readBytes(4);
//
//                byte[] lowBytes = stream.readBytes(4);
//            }
//            else if (tag == Constant.CONSTANT_Class_info) {
//                int nameIndex = stream.readUnsignedShort();
//                System.out.println("  nameIndex: " + nameIndex);
//            }
//            else if (tag == Constant.CONSTANT_String_info) {
//                int stringIndex = stream.readUnsignedShort();
//            }
//            else if (tag == Constant.CONSTANT_Fieldres_info) {
//                int classIndex = stream.readUnsignedShort();
//                int nameAndTypeIndex = stream.readUnsignedShort();
//            }
//            else if (tag == Constant.CONSTANT_Methodref_info) {
//                int classIndex = stream.readUnsignedShort();
//                int nameAndTypeIndex = stream.readUnsignedShort();
//            }
//            else if (tag == Constant.CONSTANT_InterfaceMethodref_info) {
//                int classIndex = stream.readUnsignedShort();
//                int nameAndTypeIndex = stream.readUnsignedShort();
//            }
//            else if (tag == Constant.CONSTANT_NameAndType_info) {
//                int nameIndex = stream.readUnsignedShort();
//                int descriptorIndex = stream.readUnsignedShort();
//            }
//            else if (tag == Constant.CONSTANT_MethodHandle_info) {
//                int referenceKind = stream.readUnsignedByte();
//                int referenceIndex = stream.readUnsignedShort();
//            }
//            else if (tag == Constant.CONSTANT_MethodType_info) {
//                int descriptorIndex = stream.readUnsignedShort();
//            }
//            else if (tag == Constant.CONSTANT_InvokeDynamic_info) {
//                int bootstrapMethodAttrIndex = stream.readUnsignedShort();
//                int nameAndTypeIndex = stream.readUnsignedShort();
//            }
//
//            constantPoolCount--;
//            index++;
//        }

        int accessFlags = stream.readUnsignedShort();
        printlnAccessFlags(accessFlags);

        int thisClass = stream.readUnsignedShort();
        System.out.println("thisClass: " + thisClass);

        int superClass = stream.readUnsignedShort();
        System.out.println("superClass: " + superClass);

        int interfacesCount = stream.readUnsignedShort();
        System.out.println("interfacesCount: " + interfacesCount);

        while (interfacesCount > 0) {
            int classInfoIndex = stream.readUnsignedShort();
            interfacesCount--;
        }

        int fieldsCount = stream.readUnsignedShort();
        System.out.println("fieldsCount: " + fieldsCount);

        while (fieldsCount > 0) {
            int fieldAccessFlags = stream.readUnsignedShort();
            printlnAccessFlags(fieldAccessFlags);

            int nameIndex = stream.readUnsignedShort();
            System.out.println("  field-nameIndex: " + nameIndex);
            int descriptorIndex = stream.readUnsignedShort();
            int attributesCount = stream.readUnsignedShort();
            System.out.println("  field-attributesCount: " + attributesCount);

            while (attributesCount > 0) {
                int attributeNameIndex = stream.readUnsignedShort();
                long attributeLength = stream.readUnsignedInt();

                //跳过字段的附加属性

                stream.readBytes((int) attributeLength);

                attributesCount--;
            }
            fieldsCount--;
        }

        int methodsCount = stream.readUnsignedShort();
        System.out.println("methodsCount: " + methodsCount);

        while (methodsCount > 0) {
            int methodAccessFlags = stream.readUnsignedShort();

            printlnAccessFlags(methodAccessFlags);

            int nameIndex = stream.readUnsignedShort();

            System.out.println("  method-nameIndex: " + nameIndex);
            int descriptorIndex = stream.readUnsignedShort();

            System.out.println("  method-descriptorIndex: " + descriptorIndex);
            int attributesCount = stream.readUnsignedShort();

            System.out.println("  method-attributesCount: " + attributesCount);

            while (attributesCount > 0) {
                int attributeNameIndex = stream.readUnsignedShort();
                long attributeLength = stream.readUnsignedInt();
                //跳过方法的附加属性
                stream.readBytes((int) attributeLength);

                attributesCount--;
            }

            methodsCount--;
        }

        int attributesCount = stream.readUnsignedShort();
        System.out.println("class-attributesCount: " + attributesCount);

        while (attributesCount > 0) {
            int attributeNameIndex = stream.readUnsignedShort();
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
