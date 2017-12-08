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
        int number_of_classes = stream.readUnsignedShort();

        while (number_of_classes > 0) {
            int inner_class_info_index = stream.readUnsignedShort();
            ClassConstant inner_class_info = (ClassConstant) constantList.get(inner_class_info_index - 1);
            LogUtil.d("inner_class_info: " + inner_class_info.getNameString());

            int outer_class_info_index = stream.readUnsignedShort();

            if (outer_class_info_index == 0) {
                LogUtil.d("outerClassInfo: " + null);
            }
            else {
                ClassConstant outer_class_info = (ClassConstant) constantList.get(outer_class_info_index - 1);
                LogUtil.d("outer_class_info: " + outer_class_info.getNameString());
            }

            int inner_name_index = stream.readUnsignedShort();

            if (inner_name_index == 0) {
                LogUtil.d("innerName: " + null);
            }
            else {
                Utf8Constant innerName = (Utf8Constant) constantList.get(inner_name_index - 1);
                LogUtil.d("innerName: " + innerName);
            }

            int innerClassAccessFlags = stream.readUnsignedShort();

            Utils.printlnAccessFlags(innerClassAccessFlags);
            number_of_classes--;
        }
    }

    @Override
    public void postHandle(List<Constant> constantList) {

    }
}
