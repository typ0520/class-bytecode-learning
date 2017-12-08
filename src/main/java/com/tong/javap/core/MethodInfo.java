package com.tong.javap.core;

import com.tong.javap.core.attrs.Attribute;
import com.tong.javap.core.contant.level0.Utf8Constant;
import java.util.List;

/**
 * Created by tong on 2017/12/6.
 */
public class MethodInfo extends FieldInfo {
    public MethodInfo() {
    }

    public MethodInfo(int accessFlags, Utf8Constant name, Utf8Constant descriptor, List<Attribute> attributes) {
        super(accessFlags, name, descriptor, attributes);
    }
}
