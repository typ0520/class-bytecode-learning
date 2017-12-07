package com.tong.javap.core.attrs;

import com.tong.javap.core.contant.Constant;
import com.tong.javap.core.contant.level0.Utf8Constant;
import com.tong.javap.core.contant.level1.ClassConstant;
import com.tong.javap.core.utils.ByteCodeStream;
import com.tong.javap.core.utils.LogUtil;
import com.tong.javap.core.utils.Utils;

import java.util.List;

/**
 * Created by tong on 2017/12/6.
 */
public class InnerClassesAttribute extends Attribute {
    public static final String NAME = "InnerClasses";

    @Override
    public void preHandle(ByteCodeStream stream,List<Constant> constantList) {
        int classesCount = stream.readUnsignedShort();

        while (classesCount > 0) {
            int inner_class_index = stream.readUnsignedShort();
            ClassConstant innerClassInfo = (ClassConstant) constantList.get(inner_class_index - 1);
            LogUtil.d("innerClassInfo: " + innerClassInfo.getNameString());

            int outer_class_index = stream.readUnsignedShort();

            if (outer_class_index == 0) {
                LogUtil.d("outerClassInfo: " + null);
            }
            else {
                ClassConstant outerClassInfo = (ClassConstant) constantList.get(outer_class_index - 1);
                LogUtil.d("outerClassInfo: " + outerClassInfo.getNameString());
            }

            Utf8Constant innerName = (Utf8Constant) constantList.get(stream.readUnsignedShort() - 1);
            LogUtil.d("innerName: " + innerName);
            int innerClassAccessFlags = stream.readUnsignedShort();

            Utils.printlnAccessFlags(innerClassAccessFlags);
            classesCount--;
        }
    }

    @Override
    public void postHandle(List<Constant> constantList) {

    }
}
