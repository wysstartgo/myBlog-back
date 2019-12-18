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
 * @company: 润投科技
 * @author: wuys
 * @datetime: 2019-12-18 16:49
 * </pre>
 */
data class SysGroup(
        @TableId(value = "id", type = IdType.AUTO)
        private var id: Long? = null,

        /**
         * 组名称
         */
        private var groupName: String? = null,

        /**
         * 描述
         */
        private var description: String? = null,

        /**
         * 是否有效
         */
        private var isValid: Int? = null,

        /**
         * 创建时间
         */
        private var createTime: Date? = null,

        /**
         * 创建者id
         */
        private var createUserId: Long? = null,

        /**
         * 更新时间
         */
        private var updateTime: Date? = null,

        /**
         * 更新者id
         */
        private var updateUserId: Long? = null,

        /**
         * 更新者用户名
         */
        private var updateUserName: String? = null,

        /**
         * 创建者用户名
         */
        private var createUserName: String? = null)