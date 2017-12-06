package com.tong.javap.core.utils;

import java.lang.reflect.Modifier;

/**
 * Created by tong on 2017/12/6.
 */
public class Utils {
    public static void printlnAccessFlags(int accessFlags) {
        StringBuilder sb = new StringBuilder();
        if (Modifier.isPublic(accessFlags)) {
            sb.append("| public");
        }
        if (Modifier.isPrivate(accessFlags)) {
            sb.append("| private");
        }
        if (Modifier.isProtected(accessFlags)) {
            sb.append("| protected");
        }
        if (Modifier.isAbstract(accessFlags)) {
            sb.append("| abstract");
        }
        if (Modifier.isFinal(accessFlags)) {
            sb.append("| final");
        }
        if (Modifier.isStatic(accessFlags)) {
            sb.append("| static");
        }
        if (Modifier.isVolatile(accessFlags)) {
            sb.append("| volatile");
        }

        LogUtil.d(accessFlags + " " + sb.toString());
        //......
    }
}
