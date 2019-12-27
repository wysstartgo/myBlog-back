package com.ambition.common.enums

/**
 * <pre>
 *
 *
 * @title: ContentCategoryEnum
 * @description:
 * @author: wuys
 * @datetime: 2019-12-24 15:42
 * </pre>
 */
enum class ContentCategoryEnum(val code: Int, val msg: String) {

    Tangshi(1,"唐诗"),Shongchi(2, "宋词");



    fun check(value: Int?): Boolean {
        if (value != null) {
            for (c in ContentCategoryEnum.values()) {
                if (c.code == value) {
                    return true
                }
            }
        }
        return false
    }


    fun eval(value: Int): ContentCategoryEnum? {
        for (type in ContentCategoryEnum.values()) {
            if (type.code == value) {
                return type
            }
        }
        return null
    }

}