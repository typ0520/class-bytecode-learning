package com.tong.javap.core;

import com.tong.javap.core.attrs.Attribute;
import com.tong.javap.core.attrs.SourceFileAttribute;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 * Created by tong on 2017/12/8.
 */
public class JavapClassPrinter {
    public void print(ClassFile classFile) {
        SourceFileAttribute sourceFileAttribute = findSourceFileAttribute(classFile.getAttributes());
        StringBuilder sb = new StringBuilder();
        sb.append("  Compiled from \"" + sourceFileAttribute.getSourcefile().getValue() + "\"");
        sb.append("\n");

        sb.append(getModifier(classFile.getAccessFlags()));
        sb.append(" ");
        sb.append(getClassType(classFile.getAccessFlags()));
        sb.append(" ");
        sb.append(classFile.getThisClass().getNameString().replaceAll("/","."));

        sb.append("\n");
        sb.append("  minor version: " + classFile.getMinorVersion());
        sb.append("\n");
        sb.append("  major version: " + classFile.getMajorVersion());
        sb.append("\n");
        sb.append("  flags: " + getFlags(classFile.getAccessFlags()));

        System.out.println(sb.toString());
    }

    private String getClassType(int accessFlags) {
        if ((accessFlags & ClassFile.ACC_INTERFACE) != 0) {
            return "interface";
        }
        else if ((accessFlags & ClassFile.ACC_ENUM) != 0) {
            return "enum";
        }
        else {
            return "class";
        }
    }

    public String getModifier(int accessFlags) {
        StringBuilder sb = new StringBuilder();
        if (Modifier.isPublic(accessFlags)) {
            sb.append(" public");
        }
        if (Modifier.isProtected(accessFlags)) {
            sb.append(" protected");
        }
        if (Modifier.isPrivate(accessFlags)) {
            sb.append(" private");
        }
        if (Modifier.is(accessFlags)) {
            sb.append(" private");
        }
        if (Modifier.isFinal(accessFlags)) {
            sb.append(" final");
        }
        if (Modifier.isAbstract(accessFlags)) {
            sb.append(" abstract");
        }
        sb.deleteCharAt(0);
        return sb.toString();
    }

    public String getFlags(int accessFlags) {
        StringBuilder sb = new StringBuilder();
        if (Modifier.isPublic(accessFlags)) {
            sb.append(" ACC_PUBLIC");
        }
        if (Modifier.isProtected(accessFlags)) {
            sb.append(" ACC_PROTECTED");
        }
        if (Modifier.isPrivate(accessFlags)) {
            sb.append(" ACC_PRIVATE");
        }
        if (Modifier.isFinal(accessFlags)) {
            sb.append(" ACC_FINAL");
        }
        if (Modifier.isAbstract(accessFlags)) {
            sb.append(" abstract");
        }
        sb.deleteCharAt(0);
        return sb.toString();
    }

    private SourceFileAttribute findSourceFileAttribute(List<Attribute> attributes) {
        for (Attribute attribute : attributes) {
            if (attribute instanceof SourceFileAttribute) {
                return (SourceFileAttribute) attribute;
            }
        }
        return null;
    }
}
