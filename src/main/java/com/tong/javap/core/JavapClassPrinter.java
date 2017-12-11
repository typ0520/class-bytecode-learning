package com.tong.javap.core;

import com.tong.javap.core.attrs.*;
import com.tong.javap.core.contant.Constant;
import com.tong.javap.core.contant.level0.Utf8Constant;
import com.tong.javap.core.contant.level0.ValueConstant;
import com.tong.javap.core.contant.level1.ClassConstant;
import com.tong.javap.core.contant.level1.NameAndTypeConstant;
import com.tong.javap.core.contant.level1.StringConstant;
import com.tong.javap.core.contant.level2.BaseClassNameAndTypeConstant;
import com.tong.javap.core.contant.level2.MethodrefConstant;

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
        sb.append("\n");
        sb.append("Constant pool: \n");

        for (int i = 0; i < classFile.getConstants().size(); i++) {
            Constant constant = classFile.getConstants().get(i);
            sb.append("  #" + (i + 1));
            sb.append(" = ");
            sb.append(constant.getDesc());
            sb.append("  ");

            if (constant instanceof Utf8Constant) {
                sb.append("  " + constant.toString());
            }
            else if (constant instanceof NameAndTypeConstant) {
                NameAndTypeConstant nameAndTypeConstant = (NameAndTypeConstant) constant;
                sb.append("#" + nameAndTypeConstant.getNameIndex());
                sb.append(":#" + nameAndTypeConstant.getDescriptorIndex());
                sb.append("    // ");
                sb.append(nameAndTypeConstant.getNameString());
                sb.append(":");
                sb.append(nameAndTypeConstant.getDescriptorString());
            }
            else if (constant instanceof ClassConstant) {
                sb.append("#" + ((ClassConstant) constant).getNameIndex());
                sb.append("    // ");
                sb.append(((ClassConstant) constant).getNameString());
            }
            else if (constant instanceof BaseClassNameAndTypeConstant) {
                BaseClassNameAndTypeConstant baseClassNameAndTypeConstant = (BaseClassNameAndTypeConstant) constant;
                sb.append("#" +  baseClassNameAndTypeConstant.getClassIndex());
                sb.append(".");
                sb.append(baseClassNameAndTypeConstant.getNameAndTypeIndex());
                sb.append("    // ");
                sb.append(baseClassNameAndTypeConstant.getClazz().getNameString() + ".");
                sb.append("\"" + baseClassNameAndTypeConstant.getNameAndType().getNameString() + "\":");
                sb.append(baseClassNameAndTypeConstant.getNameAndType().getDescriptorString());
            }
            else if (constant instanceof StringConstant) {
                sb.append("#" + ((StringConstant) constant).getStringIndex());
                sb.append("    // ");
                sb.append(((StringConstant) constant).getValueString());
            }
            else if (constant instanceof ValueConstant) {
                sb.append("  " + ((ValueConstant) constant).getValue());
            }
            sb.append("\n");
        }

        for (Attribute attribute : classFile.getAttributes()) {
            sb.append(attribute.getName() + ": ");

            if (attribute instanceof SignatureAttribute) {
                sb.append("#" + ((SignatureAttribute) attribute).getSignatureIndex());
                sb.append("    // ");
                sb.append(attribute.getName().getValue());
            }
            else if (attribute instanceof SourceFileAttribute) {
                sb.append("\"" + ((SourceFileAttribute) attribute).getSourcefile().getValue() + "\"");
            }
            else if (attribute instanceof BaseAnnotationsAttribute) {
                sb.append("\n");

                BaseAnnotationsAttribute baseAnnotationsAttribute = (BaseAnnotationsAttribute) attribute;
                for (int i = 0; i < baseAnnotationsAttribute.getElementAnnotations().size(); i++) {
                    ElementAnnotation elementAnnotation = baseAnnotationsAttribute.getElementAnnotations().get(i);
                    sb.append("  " + (i + 0) + ": #" + elementAnnotation.getTypeIndex());
                }
            }
            else if (attribute instanceof InnerClassesAttribute) {

            }

            sb.append("\n");
        }
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
        if (Modifier.isStatic(accessFlags)) {
            sb.append(" static");
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
            sb.append(" ACC_ABSTRACT");
        }
        if ((accessFlags & ClassFile.ACC_SUPER) != 0) {
            sb.append(" ACC_SUPER");
        }
        if ((accessFlags & ClassFile.ACC_INTERFACE) != 0) {
            sb.append(" ACC_INTERFACE");
        }
        if ((accessFlags & ClassFile.ACC_ENUM) != 0) {
            sb.append(" ACC_ENUM");
        }
        if ((accessFlags & ClassFile.ACC_SYNTHETIC) != 0) {
            sb.append(" ACC_SYNTHETIC");
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
