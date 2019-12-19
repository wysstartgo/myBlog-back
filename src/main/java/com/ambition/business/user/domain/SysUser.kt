package com.ambition.business.user.domain

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import java.io.Serializable
import java.util.*


/**
 * <pre>
 *
 *
 * @title: SysUser
 * @description:
 * @company: 润投科技
 * @author: wuys
 * @datetime: 2019-12-19 10:27
 * </pre>
 */
open class SysUser : Serializable {

    @TableId(value = "id", type = IdType.AUTO)
     var id: Long? = null

    /**
     * 用户名
     */
     var username: String? = null

    /**
     * 密码
     */
     var password: String? = null

    /**
     * 昵称
     */
     var nickname: String? = null

    /**
     * 角色ID
     */
     var roleId: Int? = null

    /**
     * 创建时间
     */
     var createTime: Date? = null

    /**
     * 修改时间
     */
     var updateTime: Date? = null

    /**
     * 是否有效  1有效  2无效
     */
     var deleteStatus: String? = null

    /**
     * 所属组织id
     */
     var groupId: Long? = null

    /**
     * 所属组织名称
     */
     var groupName: String? = null

     var createUserId: Long? = null

     var createUserName: String? = null

     var updateUserId: Long? = null

     var updateUserName: String? = null

    /**
     * 1是男0是女
     */
     var gender: Int? = null

    /**
     * 电话号码
     */
     var phoneNumber: String? = null

    /**
     * 身份证号码
     */
     var idCard: String? = null

    /**
     * 专业领域
     */
     var specialArea: String? = null

    /**
     * 职称
     */
     var professionalTile: String? = null

    /**
     * 二维码
     */
     var qrCode: String? = null

     var userImgUrl: String? = null

    /**
     * 简介
     */
     var description: String? = null

    companion object {

         const val serialVersionUID = 1L
    }


}
