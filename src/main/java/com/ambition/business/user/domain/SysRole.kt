package com.ambition.business.user.domain

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import java.io.Serializable
import java.util.*


/**
 * <pre>
 *
 *
 * @title: SysRole
 * @description:
 * @company: 润投科技
 * @author: wuys
 * @datetime: 2019-12-19 10:40
 * </pre>
 */
class SysRole : Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    val id: Int? = null

    /**
     * 角色名
     */
    val roleName: String? = null

    val createTime: Date? = null

    val updateTime: Date? = null

    /**
     * 是否有效  1有效  2无效
     */
    val deleteStatus: String? = null

    /**
     * 组织id
     */
    val groupId: Long? = null

    /**
     * 组织名称
     */
    val groupName: String? = null

    /**
     * 创建者id
     */
    val createUserId: Long? = null

    val createUserName: String? = null

    companion object {

        const val serialVersionUID = 1L
    }


}
