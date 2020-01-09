package com.ambition.business.user.domain

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import java.io.Serializable
import java.util.*


/**
 * <pre>
 *
 *
 * @title: SysRolePermission
 * @description:
 * @company: 润投科技
 * @author: wuys
 * @datetime: 2019-12-19 10:39
 * </pre>
 */
class SysRolePermission : Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    val id: Int? = null

    /**
     * 角色id
     */
    val roleId: Int? = null

    /**
     * 权限id
     */
    val permissionId: Int? = null

    val createTime: Date? = null

    val updateTime: Date? = null

    /**
     * 是否有效 1有效     2无效
     */
    val deleteStatus: String? = null

    companion object {

        const val serialVersionUID = 1L
    }


}
