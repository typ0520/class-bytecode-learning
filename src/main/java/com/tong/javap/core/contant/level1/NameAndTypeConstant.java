package com.tong.javap.core.contant.level1;

import com.tong.javap.core.contant.Constant;
import com.tong.javap.core.contant.level0.Utf8Constant;
import com.tong.javap.core.utils.ByteCodeStream;
import java.util.List;

/**
 * Created by tong on 2017/12/1.
 */
public class NameAndTypeConstant extends Constant {
    private int nameIndex;
    private int descriptorIndex;

    private Utf8Constant name;
    private Utf8Constant descriptor;

    public NameAndTypeConstant() {
        super(Constant.CONSTANT_NameAndType_info, "NameAndType");
    }

    @Override
    public void preHandle(ByteCodeStream stream) {
        nameIndex = stream.readUnsignedShort();
        descriptorIndex = stream.readUnsignedShort();
    }

    @Override
    public void postHandle(List<Constant> constantList) {
        this.name = (Utf8Constant) constantList.get(nameIndex - 1);
        this.descriptor = (Utf8Constant) constantList.get(descriptorIndex - 1);

        setReady(true);
    }

    public Utf8Constant getName() {
        return name;
    }

    public Utf8Constant getDescriptor() {
        return descriptor;
    }

    public String getNameString() {
        return getName().getValue();
    }

    public String getDescriptorString() {
        return getDescriptor().getValue();
    }
}