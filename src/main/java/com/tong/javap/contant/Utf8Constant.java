package com.tong.javap.contant;

/**
 * Created by tong on 2017/12/1.
 */
public class Utf8Constant extends Constant {
    private final String content;

    public Utf8Constant(String content) {
        super(Constant.CONSTANT_Utf8_info,"Utf8");
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
