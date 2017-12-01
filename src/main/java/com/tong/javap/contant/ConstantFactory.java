package com.tong.javap.contant;

import com.tong.javap.contant.level0.*;
import com.tong.javap.contant.level1.*;
import com.tong.javap.contant.level2.*;
import com.tong.javap.utils.ByteCodeStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tong on 2017/12/1.
 */
public class ConstantFactory {
    private static final Map<Integer,Class> CONSTANT_TYPE_MAP = new HashMap<Integer, Class>();

    static {
        CONSTANT_TYPE_MAP.put(Constant.CONSTANT_Utf8_info,Utf8Constant.class);
        CONSTANT_TYPE_MAP.put(Constant.CONSTANT_Integer_info,IntegerConstant.class);
        CONSTANT_TYPE_MAP.put(Constant.CONSTANT_Float_info,FloatConstant.class);
        CONSTANT_TYPE_MAP.put(Constant.CONSTANT_Long_info,LongConstant.class);
        CONSTANT_TYPE_MAP.put(Constant.CONSTANT_Double_info,DoubleConstant.class);
        CONSTANT_TYPE_MAP.put(Constant.CONSTANT_Class_info,ClassConstant.class);
        CONSTANT_TYPE_MAP.put(Constant.CONSTANT_String_info,StringConstant.class);
        CONSTANT_TYPE_MAP.put(Constant.CONSTANT_Fieldres_info,FieldresConstant.class);
        CONSTANT_TYPE_MAP.put(Constant.CONSTANT_Methodref_info,MethodrefConstant.class);
        CONSTANT_TYPE_MAP.put(Constant.CONSTANT_InterfaceMethodref_info,InterfaceMethodrefConstant.class);
        CONSTANT_TYPE_MAP.put(Constant.CONSTANT_NameAndType_info,NameAndTypeConstant.class);
        CONSTANT_TYPE_MAP.put(Constant.CONSTANT_MethodHandle_info,MethodHandleConstant.class);
        CONSTANT_TYPE_MAP.put(Constant.CONSTANT_MethodType_info,MethodTypeConstant.class);
        CONSTANT_TYPE_MAP.put(Constant.CONSTANT_InvokeDynamic_info,InvokeDynamicConstant.class);
    }

    public List<Constant> parse(int constantPoolCount,ByteCodeStream stream) {
        final List<Constant> constantList = new ArrayList<Constant>();

        int index = 1;
        while ((constantPoolCount - 1) > 0) {
            int tag = stream.readUnsignedByte();

            Constant constant = createConstant(tag);
            constant.preHandle(stream);
            System.out.println("#" + index + " = " + constant.getDesc());
            constantList.add(constant);

            constantPoolCount--;
            index++;
        }

        int postCount = constantList.size() * 10;
        boolean ready = false;
        while (!ready && postCount > 0) {
            ready = true;
            for (Constant constant : constantList) {
                if (!constant.isReady()) {
                    ready = false;
                    constant.postHandle(constantList);
                }
            }

            postCount--;
        }

        if (postCount <= 0) {
            throw new IllegalStateException("error");
        }
        return constantList;
    }

    private Constant createConstant(int tag) {
        Class constantClass = CONSTANT_TYPE_MAP.get(tag);
        if (constantClass == null) {
            throw new RuntimeException("Unrecognized constant tag: " + tag);
        }
        try {
            return (Constant) constantClass.newInstance();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
