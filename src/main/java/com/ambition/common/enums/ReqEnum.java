package com.ambition.common.enums;

/**
 * 请求枚举类
 */
public enum ReqEnum {
    /**
     * pc
     */
    PC("pc","pc请求"),
    /**
     * h5
     */
    H5("h5", "H5请求"),
    /**
     * android
     */
    ANDROID("android", "安卓端请求"),
    /**
     * ios
     */
    IOS("ios", "ios端请求");

    private String value;
    private String msg;


    ReqEnum(String value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static boolean check(String value) {
        if (value != null) {
            for (ReqEnum c : ReqEnum.values()) {
                if (c.getValue().equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }



    public static ReqEnum eval(String value) {
        for (ReqEnum type : ReqEnum.values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }

}
