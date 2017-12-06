package com.tong.javap.core;

import com.tong.javap.core.contant.Constant;
import com.tong.javap.core.contant.level1.ClassConstant;
import java.util.List;

/**
 * Created by tong on 2017/12/6.
 */
public class ClassInfo {
    //魔数
    private long magicNumber;
    //次版本号
    private int minorVersion;
    //主版本号
    private int majorVersion;
    //常量列表
    private List<Constant> constantList;

    private int accessFlags;

    private ClassConstant thisClass;

    private ClassConstant superClass;

    private List<ClassConstant> interfaces;
}
