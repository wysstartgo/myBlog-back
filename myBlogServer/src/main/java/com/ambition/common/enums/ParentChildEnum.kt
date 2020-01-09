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
enum class ParentChildEnum(val code: Int, val msg: String) {

    Child(0,"最下级结点"),Parent(9, "父级结点");



    fun check(value: Int?): Boolean {
        if (value != null) {
            for (c in ParentChildEnum.values()) {
                if (c.code == value) {
                    return true
                }
            }
        }
        return false
    }


    fun eval(value: Int): ParentChildEnum? {
        for (type in ParentChildEnum.values()) {
            if (type.code == value) {
                return type
            }
        }
        return null
    }

}