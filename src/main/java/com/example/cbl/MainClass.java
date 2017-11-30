package com.example.cbl;

import com.example.cbl.contant.Constant;
import org.fenixsoft.clazz.TestClass;
import java.io.*;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by tong on 2017/11/29.
 */
public class MainClass {
    public static void main(String[] args) throws Exception {
        byte[] classBytes = getClassBytes(TestClass.class);
        int offset = 0;

        //正确的魔术是0xcafebabe
        long magicNumber = readUnsignedInt(classBytes,0);
        offset += 4;
        System.out.println("获取到魔数: " + Long.toHexString(magicNumber));

        //获取版本信息
        int minorVersion = readUnsignedShort(classBytes,offset);
        offset += 2;
        int majorVersion = readUnsignedShort(classBytes,offset);
        offset += 2;
        System.out.println("minor version: " + minorVersion);
        System.out.println("major version: " + majorVersion);

        //获取常量池大小
        int constantPoolCount = readUnsignedShort(classBytes,offset);
        offset += 2;
        System.out.println("常量池大小: " + constantPoolCount);

        //常量池起始点
        int constantPoolOffset = offset;
        int index = 1;
        while ((constantPoolCount - 1) > 0) {
            int tag = readUnsignedByte(classBytes,offset);
            offset += 1;
            System.out.println("#" + index + " = " + Constant.getContantDesc(tag));
            if (tag == Constant.CONSTANT_Utf8_info) {
                int length = readUnsignedShort(classBytes,offset);
                offset += 2;
                byte[] bytes = readBytes(classBytes,offset,length);
                offset += length;

                System.out.println("  读取到了字符串常量: " + new String(bytes));
            }
            else if (tag == Constant.CONSTANT_Integer_info) {
                byte[] bytes = readBytes(classBytes,offset,4);
                offset += 4;
            }
            else if (tag == Constant.CONSTANT_Float_info) {
                byte[] bytes = readBytes(classBytes,offset,4);
                offset += 4;
            }
            else if (tag == Constant.CONSTANT_Long_info) {
                byte[] highBytes = readBytes(classBytes,offset,4);
                offset += 4;

                byte[] lowBytes = readBytes(classBytes,offset,4);
                offset += 4;
            }
            else if (tag == Constant.CONSTANT_Double_info) {
                byte[] highBytes = readBytes(classBytes,offset,4);
                offset += 4;

                byte[] lowBytes = readBytes(classBytes,offset,4);
                offset += 4;
            }
            else if (tag == Constant.CONSTANT_Class_info) {
                int nameIndex = readUnsignedShort(classBytes,offset);
                offset += 2;
                System.out.println("  nameIndex: " + nameIndex);
            }
            else if (tag == Constant.CONSTANT_String_info) {
                int stringIndex = readUnsignedShort(classBytes,offset);
                offset += 2;
            }
            else if (tag == Constant.CONSTANT_Fieldres_info) {
                int classIndex = readUnsignedShort(classBytes,offset);
                offset += 2;
                int nameAndTypeIndex = readUnsignedShort(classBytes,offset);
                offset += 2;
            }
            else if (tag == Constant.CONSTANT_Methodref_info) {
                int classIndex = readUnsignedShort(classBytes,offset);
                offset += 2;
                int nameAndTypeIndex = readUnsignedShort(classBytes,offset);
                offset += 2;
            }
            else if (tag == Constant.CONSTANT_InterfaceMethodref_info) {
                int classIndex = readUnsignedShort(classBytes,offset);
                offset += 2;
                int nameAndTypeIndex = readUnsignedShort(classBytes,offset);
                offset += 2;
            }
            else if (tag == Constant.CONSTANT_NameAndType_info) {
                int nameIndex = readUnsignedShort(classBytes,offset);
                offset += 2;
                int descriptorIndex = readUnsignedShort(classBytes,offset);
                offset += 2;
            }
            else if (tag == Constant.CONSTANT_MethodHandle_info) {
                int referenceKind = readUnsignedByte(classBytes,1);
                offset += 1;
                int referenceIndex = readUnsignedShort(classBytes,2);
                offset += 2;
            }
            else if (tag == Constant.CONSTANT_MethodType_info) {
                int descriptorIndex = readUnsignedShort(classBytes,offset);
                offset += 2;
            }
            else if (tag == Constant.CONSTANT_InvokeDynamic_info) {
                int bootstrapMethodAttrIndex = readUnsignedShort(classBytes,offset);
                offset += 2;
                int nameAndTypeIndex = readUnsignedShort(classBytes,offset);
                offset += 2;
            }

            constantPoolCount--;
            index++;
        }

        int accessFlags = readUnsignedShort(classBytes,offset);
        offset += 2;
        printlnAccessFlags(accessFlags);

        int thisClass = readUnsignedShort(classBytes,offset);
        offset += 2;
        System.out.println("thisClass: " + thisClass);

        int superClass = readUnsignedShort(classBytes,offset);
        offset += 2;
        System.out.println("superClass: " + superClass);

        int interfacesCount = readUnsignedShort(classBytes,offset);
        offset += 2;
        System.out.println("interfacesCount: " + interfacesCount);

        while (interfacesCount > 0) {
            int classInfoIndex = readUnsignedShort(classBytes,offset);
            offset += 2;
            interfacesCount--;
        }

        int fieldsCount = readUnsignedShort(classBytes,offset);
        offset += 2;
        System.out.println("fieldsCount: " + fieldsCount);

        while (fieldsCount > 0) {
            int fieldAccessFlags = readUnsignedShort(classBytes,offset);
            offset += 2;
            printlnAccessFlags(fieldAccessFlags);

            int nameIndex = readUnsignedShort(classBytes,offset);
            offset += 2;
            System.out.println("  field-nameIndex: " + nameIndex);
            int descriptorIndex = readUnsignedShort(classBytes,offset);
            offset += 2;
            int attributesCount = readUnsignedShort(classBytes,offset);
            offset += 2;
            System.out.println("  field-attributesCount: " + attributesCount);

            while (attributesCount > 0) {
                int attributeNameIndex = readUnsignedShort(classBytes,offset);
                offset += 2;
                long attributeLength = readUnsignedInt(classBytes,offset);
                offset += 4;

                //跳过字段的附加属性
                offset += attributeLength;

                attributesCount--;
            }
            fieldsCount--;
        }

        int methodsCount = readUnsignedShort(classBytes,offset);
        offset += 2;
        System.out.println("methodsCount: " + methodsCount);

        while (methodsCount > 0) {
            int methodAccessFlags = readUnsignedShort(classBytes,offset);
            offset += 2;
            printlnAccessFlags(methodAccessFlags);

            int nameIndex = readUnsignedShort(classBytes,offset);
            offset += 2;
            System.out.println("  method-nameIndex: " + nameIndex);
            int descriptorIndex = readUnsignedShort(classBytes,offset);
            offset += 2;
            System.out.println("  method-descriptorIndex: " + descriptorIndex);
            int attributesCount = readUnsignedShort(classBytes,offset);
            offset += 2;
            System.out.println("  method-attributesCount: " + attributesCount);

            while (attributesCount > 0) {
                int attributeNameIndex = readUnsignedShort(classBytes,offset);
                offset += 2;

                long attributeLength = readUnsignedInt(classBytes,offset);
                offset += 4;


                //跳过方法的附加属性
                offset += attributeLength;

                attributesCount--;
            }

            methodsCount--;
        }

        int attributesCount = readUnsignedShort(classBytes,offset);
        offset += 2;
        System.out.println("class-attributesCount: " + attributesCount);

        while (attributesCount > 0) {
            int attributeNameIndex = readUnsignedShort(classBytes,offset);
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

    /**
     * 获取class的字节码
     * @param clazz
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public static byte[] getClassBytes(Class clazz) throws IOException, URISyntaxException {
        URL url = MainClass.class.getClassLoader().getResource("");
        File rootPath = new File(url.toURI());
        File classFile = new File(rootPath,clazz.getName().replaceAll("\\.",File.separator) + ".class");
        System.out.println("加载字节码: " + classFile);

        FileInputStream fis = new FileInputStream(classFile);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        try {
            while ((len = fis.read(buffer)) != -1) {
                bos.write(buffer,0,len);
            }
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
        return bos.toByteArray();
    }

    /**
     * class的字节序为Big-Endian(高位在前)
     * @param classBytes
     * @param offset
     * @return
     */
    public static int readUnsignedByte(byte[] classBytes, int offset) {
        return Byte.toUnsignedInt(classBytes[offset]);
    }

    /**
     * class的字节序为Big-Endian(高位在前)
     * @param classBytes
     * @param offset
     * @return
     */
    public static int readUnsignedShort(byte[] classBytes, int offset) {
        return (((classBytes[offset] & 0xFF) << 8) | (classBytes[offset + 1] & 0xFF)) & 0xffff;
    }

    /**
     * class的字节序为Big-Endian(高位在前)
     * @param classBytes
     * @param offset
     * @return
     */
    public static long readUnsignedInt(byte[] classBytes, int offset) {
        return Integer.toUnsignedLong(((classBytes[offset] & 0xFF) << 24) | ((classBytes[offset + 1] & 0xFF) << 16) | ((classBytes[offset + 2] & 0xFF) << 8) | (classBytes[offset + 3] & 0xFF));
    }

    /**
     * 读取字节数组
     * @param classBytes
     * @param offset
     * @param length
     * @return
     */
    public static byte[] readBytes(byte[] classBytes, int offset, int length) {
        byte[] result = new byte[length];
        System.arraycopy(classBytes,offset,result,0,length);
        return result;
    }
}
