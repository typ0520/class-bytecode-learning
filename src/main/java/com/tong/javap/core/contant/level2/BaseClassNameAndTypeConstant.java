package com.tong.javap.core.contant.level2;

import com.tong.javap.core.contant.Constant;
import com.tong.javap.core.contant.level1.ClassConstant;
import com.tong.javap.core.contant.level1.NameAndTypeConstant;
import com.tong.javap.core.utils.ByteCodeStream;
import java.util.List;

/**
 * Created by tong on 2017/12/1.
 */
public class BaseClassNameAndTypeConstant extends Constant {
    private int classIndex;
    private int nameAndTypeIndex;

    private ClassConstant clazz;
    private NameAndTypeConstant nameAndType;

    public BaseClassNameAndTypeConstant(int tag, String desc) {
        super(tag, desc);
    }

    @Override
    public void preHandle(ByteCodeStream stream) {
        classIndex = stream.readUnsignedShort();
        nameAndTypeIndex = stream.readUnsignedShort();
    }

    @Override
    public void postHandle(List<Constant> constantList) {
        this.clazz = (ClassConstant) constantList.get(classIndex - 1);
        this.nameAndType = (NameAndTypeConstant) constantList.get(nameAndTypeIndex - 1);

        setReady(true);
    }

    public int getClassIndex() {
        return classIndex;
    }

    public int getNameAndTypeIndex() {
        return nameAndTypeIndex;
    }

    public ClassConstant getClazz() {
        return clazz;
    }

    public NameAndTypeConstant getNameAndType() {
        return nameAndType;
    }
}
