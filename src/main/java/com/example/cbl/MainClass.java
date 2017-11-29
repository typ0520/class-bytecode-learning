package com.example.cbl;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by tong on 2017/11/29.
 */
public class MainClass {
    public static void main(String[] args) throws Exception {
        byte[] classBytes = getClassBytes(MainClass.class);
        getMagicNumber(classBytes);
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

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        FileInputStream fis = new FileInputStream(classFile);
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
     * 获取魔数
     * @param classBytes
     */
    public static void getMagicNumber(byte[] classBytes) {
        //正确的魔术是0xcafebabe
        int magicNumber = readInt(classBytes,0);
        System.out.println("获取到魔数: " + Integer.toHexString(magicNumber));
    }

    /**
     * class的字节序为Big-Endian(高位在前)
     * @param classBytes
     * @param offset
     * @return
     */
    public static int readInt(byte[] classBytes,int offset) {
        return ((classBytes[offset] & 0xFF) << 24) | ((classBytes[offset + 1] & 0xFF) << 16) | ((classBytes[offset + 2] & 0xFF) << 8) | (classBytes[offset + 3] & 0xFF);
    }
}
