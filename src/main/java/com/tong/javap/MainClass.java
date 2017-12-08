package com.tong.javap;

import com.tong.javap.core.ClassFile;
import com.tong.javap.core.JavapClassPrinter;
import com.tong.javap.core.utils.ByteCodeStream;

/**
 * Created by tong on 2017/11/29.
 */
public class MainClass {
    public static void main(String[] args) throws Exception {
        Class clazz = Class.forName("com.tong.javap.TestClass$1EncloseingClass");
        ByteCodeStream stream = new ByteCodeStream(TestClass.class);

        ClassFile classFile = new ClassFile(stream);
        new JavapClassPrinter().print(classFile);
    }
}
