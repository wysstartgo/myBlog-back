package com.ambition.common.enums;

/**
 * <pre>
 *  
 *
 * @title : 浏览器类型
 * @description :
 * @company :
 * @author :
 * @time: 2017年9月19日 下午5:17:06
 * </pre>
 */
public enum BrowserTypeEnum {
    /**
     * IE浏览器
     */
    IE_TYPE(10, "IE浏览器"),
    /**
     * IE浏览器
     */
    CHROME_TYPE(11, "IE浏览器"),
    /**
     * UC浏览器
     */
    UC_TYPE(12, "UC浏览器"),
    /**
     * QQ浏览器
     */
    QQ_TYPE(13, "QQ浏览器"),

    /**
     * 微信
     */
    WEIXIN_TYPE(14, "微信浏览器"),
    /**
     * WAP2.0浏览器
     */
    WAP2_TYPE(15, "WAP2.0浏览器"),

    /**
     * Oppo浏览器
     */
    OPPO_TYPE(16, "Oppo浏览器 "),
    /**
     * 百度浏览器
     */
    BAIDU_TYPE(17, "百度浏览器 "),
    /**
     * 猎豹浏览器
     */
    LIEBAO_TYPE(18, "猎豹浏览器 "),
    /**
     * IE手机浏览器
     */
    IE_PHONE_TYPE(19, "IE手机浏览器 "),
    /**
     * 360浏览器
     */
    QH360_TYPE(20, "360浏览器"),
    /**
     * 火狐浏览器
     */
    HUOHU_TYPE(21, "火狐浏览器"),
    /**
     * 其他浏览器
     */
    OTHER_TYPE(22, "其他浏览器"),
    /**
     * 安卓app浏览器
     */
    ANDROID_TYPE(1, "安卓app浏览器"),
    /**
     * Iphone app浏览器
     */
    IPHONE_APP_TYPE(2, "Iphone app浏览器");

    private Integer typeCode;

    private String typeDesc;

    BrowserTypeEnum(Integer typeCode, String typeDesc) {
        this.typeCode = typeCode;
        this.typeDesc = typeDesc;
    }

    public Integer getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(Integer typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

}
