package com.ambition.common.enums;

/**
 * <pre>
 *
 *
 * @title :
 * @description :
 * @company :
 * @author :
 * @time: 2017年9月29日 下午7:02:32
 * </pre>
 */
public enum DeviceTypeEnum {
    /**
     * pc
     */
    PC(10, "IE"),
    /**
     * android
     */
    ANDROID(11, "ANDROID"),
    /**
     * iphone
     */
    IPHONE(12, "IPHONE"),
    /**
     * ipad
     */
    IPAD(13, "IPAD"),
    /**
     * other
     */
    OTHER(99, "OTHER");

    private Integer code;

    private String name;

    DeviceTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
