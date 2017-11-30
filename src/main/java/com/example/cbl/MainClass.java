package com.example.cbl;

import org.fenixsoft.clazz.TestClass;

import java.io.*;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tong on 2017/11/29.
 */
public class MainClass {
    /**
     * UTF-8编码的字符串
     */
    private static final int CONSTANT_Utf8_info = 1;
    /**
     * 整型字面量
     */
    private static int CONSTANT_Integer_info = 3;
    /**
     * 浮点型字面量
     */
    private static int CONSTANT_Float_info = 4;
    /**
     * 长整型字面量
     */
    private static int CONSTANT_Long_info = 5;
    /**
     * 双精度浮点型字面量
     */
    private static int CONSTANT_Double_info = 6;
    /**
     * 类或者接口的符号引用
     */
    private static int CONSTANT_Class_info = 7;
    /**
     * 字符串类型字面量
     */
    private static int CONSTANT_String_info = 8;
    /**
     * 字段的符号引用
     */
    private static int CONSTANT_Fieldres_info = 9;
    /**
     * 类中方法的符号引用
     */
    private static int CONSTANT_Methodref_info = 10;
    /**
     * 接口中方法的符号引用
     */
    private static int CONSTANT_InterfaceMethodref_info = 11;
    /**
     * 字段或方法的部分符号引用
     */
    private static int CONSTANT_NameAndType_info = 12;
    /**
     * 表示方法句柄
     */
    private static int CONSTANT_MethodHandle_info = 15;
    /**
     * 标识方法类型
     */
    private static int CONSTANT_MethodType_info = 16;
    /**
     * 表示一个动态方法调用点
     */
    private static int CONSTANT_InvokeDynamic_info = 18;

    private static final Map<Integer,String> CONSTANT_DESC_MAP = new HashMap<>();

    static {
        CONSTANT_DESC_MAP.put(CONSTANT_Utf8_info,"Utf8");
        CONSTANT_DESC_MAP.put(CONSTANT_Integer_info,"Integer");
        CONSTANT_DESC_MAP.put(CONSTANT_Float_info,"Float");
        CONSTANT_DESC_MAP.put(CONSTANT_Long_info,"Long");
        CONSTANT_DESC_MAP.put(CONSTANT_Double_info,"Double");
        CONSTANT_DESC_MAP.put(CONSTANT_Class_info,"Class");
        CONSTANT_DESC_MAP.put(CONSTANT_String_info,"String");
        CONSTANT_DESC_MAP.put(CONSTANT_Fieldres_info,"Fieldres");
        CONSTANT_DESC_MAP.put(CONSTANT_Methodref_info,"Methodref");
        CONSTANT_DESC_MAP.put(CONSTANT_InterfaceMethodref_info,"InterfaceMethodref");
        CONSTANT_DESC_MAP.put(CONSTANT_NameAndType_info,"NameAndType");
        CONSTANT_DESC_MAP.put(CONSTANT_MethodHandle_info,"MethodHandle");
        CONSTANT_DESC_MAP.put(CONSTANT_MethodType_info,"MethodType");
        CONSTANT_DESC_MAP.put(CONSTANT_InvokeDynamic_info,"InvokeDynamic");
    }

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
            System.out.println("#" + index + " = " + CONSTANT_DESC_MAP.get(tag));
            if (tag == CONSTANT_Utf8_info) {
                int length = readUnsignedShort(classBytes,offset);
                offset += 2;
                byte[] bytes = readBytes(classBytes,offset,length);
                offset += length;

                System.out.println("  读取到了字符串常量: " + new String(bytes));
            }
            else if (tag == CONSTANT_Integer_info) {
                byte[] bytes = readBytes(classBytes,offset,4);
                offset += 4;
            }
            else if (tag == CONSTANT_Float_info) {
                byte[] bytes = readBytes(classBytes,offset,4);
                offset += 4;
            }
            else if (tag == CONSTANT_Long_info) {
                byte[] highBytes = readBytes(classBytes,offset,4);
                offset += 4;

                byte[] lowBytes = readBytes(classBytes,offset,4);
                offset += 4;
            }
            else if (tag == CONSTANT_Double_info) {
                byte[] highBytes = readBytes(classBytes,offset,4);
                offset += 4;

                byte[] lowBytes = readBytes(classBytes,offset,4);
                offset += 4;
            }
            else if (tag == CONSTANT_Class_info) {
                int nameIndex = readUnsignedShort(classBytes,offset);
                offset += 2;
                System.out.println("  nameIndex: " + nameIndex);
            }
            else if (tag == CONSTANT_String_info) {
                int stringIndex = readUnsignedShort(classBytes,offset);
                offset += 2;
            }
            else if (tag == CONSTANT_Fieldres_info) {
                int classIndex = readUnsignedShort(classBytes,offset);
                offset += 2;
                int nameAndTypeIndex = readUnsignedShort(classBytes,offset);
                offset += 2;
            }
            else if (tag == CONSTANT_Methodref_info) {
                int classIndex = readUnsignedShort(classBytes,offset);
                offset += 2;
                int nameAndTypeIndex = readUnsignedShort(classBytes,offset);
                offset += 2;
            }
            else if (tag == CONSTANT_InterfaceMethodref_info) {
                int classIndex = readUnsignedShort(classBytes,offset);
                offset += 2;
                int nameAndTypeIndex = readUnsignedShort(classBytes,offset);
                offset += 2;
            }
            else if (tag == CONSTANT_NameAndType_info) {
                int nameIndex = readUnsignedShort(classBytes,offset);
                offset += 2;
                int descriptorIndex = readUnsignedShort(classBytes,offset);
                offset += 2;
            }
            else if (tag == CONSTANT_MethodHandle_info) {
                int referenceKind = readUnsignedByte(classBytes,1);
                offset += 1;
                int referenceIndex = readUnsignedShort(classBytes,2);
                offset += 2;
            }
            else if (tag == CONSTANT_MethodType_info) {
                int descriptorIndex = readUnsignedShort(classBytes,offset);
                offset += 2;
            }
            else if (tag == CONSTANT_InvokeDynamic_info) {
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

            fieldsCount--;
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
