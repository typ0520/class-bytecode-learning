package com.tong.javap.core.attrs;

import com.tong.javap.core.contant.Constant;
import com.tong.javap.core.contant.level0.Utf8Constant;
import com.tong.javap.core.utils.ByteCodeStream;
import com.tong.javap.core.utils.LogUtil;
import java.util.List;

/**
 * Created by tong on 2017/12/6.
 */
public class SignatureAttribute extends Attribute {
    public static final String NAME = "Signature";

    private int signatureIndex;
    private Utf8Constant signature;

    @Override
    public void preHandle(ByteCodeStream stream,List<Constant> constantList) {
        signatureIndex = stream.readUnsignedShort();

        signature = (Utf8Constant) constantList.get(signatureIndex - 1);
        LogUtil.d(" signature: " + signature);
    }

    @Override
    public void postHandle(List<Constant> constantList) {

    }

    public int getSignatureIndex() {
        return signatureIndex;
    }

    public Utf8Constant getSignature() {
        return signature;
    }
}
