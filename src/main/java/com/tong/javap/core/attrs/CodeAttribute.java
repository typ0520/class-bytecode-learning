package com.tong.javap.core.attrs;

import com.tong.javap.core.contant.Constant;
import com.tong.javap.core.utils.ByteCodeStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tong on 2017/12/6.
 */
public class CodeAttribute extends Attribute {
    public static final String NAME = "Code";

    private int max_stack;
    private int max_locals;
    private final List<Instruct> instructs = new ArrayList<>();
    private final List<ExceptionTable> exceptionTables = new ArrayList<>();
    private final List<Attribute> attributes = new ArrayList<>();

    @Override
    public void preHandle(ByteCodeStream stream,List<Constant> constantList) {
        max_stack = stream.readUnsignedShort();
        max_locals = stream.readUnsignedShort();
        long code_length = stream.readUnsignedInt();

        while (code_length > 0) {
            int code = stream.readUnsignedByte();
            instructs.add(Instruct.valueOf(code));
            code_length--;
        }

        int exception_table_length = stream.readUnsignedShort();
        while (exception_table_length > 0) {
            exceptionTables.add(new ExceptionTable(stream,constantList));
            exception_table_length--;
        }

        int attributes_count = stream.readUnsignedShort();
        AttributeFactory attributeFactory = new AttributeFactory();

        attributes.addAll(attributeFactory.parse(attributes_count,stream,constantList));
    }

    @Override
    public void postHandle(List<Constant> constantList) {

    }

    public List<ExceptionTable> getExceptionTables() {
        return exceptionTables;
    }
}
