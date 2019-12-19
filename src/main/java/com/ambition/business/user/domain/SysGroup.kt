package com.ambition.business.user.domain

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import java.util.*

/**
 * <pre>
 *
 *
 * @title: SysGroup
 * @description:
 * @author: wuys
 * @datetime: 2019-12-18 16:49
 * </pre>
 */
data class SysGroup(
        @TableId(value = "id", type = IdType.AUTO)
         var id: Long? = null,

        /**
         * 组名称
         */
         var groupName: String? = null,

        /**
         * 描述
         */
         var description: String? = null,

        /**
         * 是否有效
         */
         var isValid: Int? = null,

        /**
         * 创建时间
         */
         var createTime: Date? = null,

        /**
         * 创建者id
         */
         var createUserId: Long? = null,

        /**
         * 更新时间
         */
         var updateTime: Date? = null,

        /**
         * 更新者id
         */
         var updateUserId: Long? = null,

        /**
         * 更新者用户名
         */
         var updateUserName: String? = null,

        /**
         * 创建者用户名
         */
         var createUserName: String? = null)