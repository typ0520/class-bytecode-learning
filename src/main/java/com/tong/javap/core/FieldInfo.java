package com.tong.javap.core;

import com.tong.javap.core.attrs.Attribute;
import com.tong.javap.core.contant.level0.Utf8Constant;
import java.util.List;

/**
 * Created by tong on 2017/12/6.
 */
public class FieldInfo {
    /**
     * 访问标志
     */
    private int accessFlags;

    /**
     * 字段名字
     */
    private Utf8Constant name;

    /**
     * 描述符
     */
    private Utf8Constant descriptor;

    /**
     * 属性表
     */
    private List<Attribute> attributes;

    public FieldInfo() {
    }

    public FieldInfo(int accessFlags, Utf8Constant name, Utf8Constant descriptor, List<Attribute> attributes) {
        this.accessFlags = accessFlags;
        this.name = name;
        this.descriptor = descriptor;
        this.attributes = attributes;
    }

    public int getAccessFlags() {
        return accessFlags;
    }

    public void setAccessFlags(int accessFlags) {
        this.accessFlags = accessFlags;
    }

    public Utf8Constant getName() {
        return name;
    }

    public void setName(Utf8Constant name) {
        this.name = name;
    }

    public Utf8Constant getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(Utf8Constant descriptor) {
        this.descriptor = descriptor;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }
}
