package com.ambition.common.enums;

/**
 * @author : 孙超飞
 * @title :
 * @description :
 * @copyright :
 * @date : 2017-11-06 9:10
 */

/**
 * 是非枚举
 */
public enum YesOrNoEnum {
    /**
     * 是
     */
    YES(1),
    /**
     * 否
     */
    NO(0);
    private Integer value;

    YesOrNoEnum(Integer value) {
        this.value = value;

    }

    public Integer getValue() {
        return value;
    }

    public static boolean check(Integer value) {
        if (value != null) {
            for (YesOrNoEnum c : YesOrNoEnum.values()) {
                if (c.getValue().equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static YesOrNoEnum eval(Integer value) {
        for (YesOrNoEnum type : YesOrNoEnum.values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }
}
