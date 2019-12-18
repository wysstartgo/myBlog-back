package com.ambition.business.user.service

import com.alibaba.fastjson.JSONObject
import com.ambition.business.user.domain.SysUser
import com.baomidou.mybatisplus.extension.service.IService

/**
 * <pre>
 *
 *
 * @title: ISysUserService
 * @description:
 * @company: 润投科技
 * @author: wuys
 * @datetime: 2019-12-18 11:53
 * </pre>
 */
interface ISysUserService : IService<SysUser> {

    /**
     * 通过用户名和密码查找用户
     * @param username
     * @param password
     * @return
     */
    fun findUserByUsernameAndPassword(username: String, password: String): SysUser

    /**
     * 用户列表
     */
    fun listUser(jsonObject: JSONObject): JSONObject

    /**
     * 查询所有的角色
     * 在添加/修改用户的时候要使用此方法
     */
    fun getAllRoles(sysUser: SysUser): JSONObject

    /**
     * 添加用户
     */
    fun addUser(sysUser: SysUser): JSONObject

    /**
     * 修改用户
     */
    fun updateUser(sysUser: SysUser): Boolean

    /**
     * 角色列表
     */
    fun listRole(sysUser: SysUser): JSONObject

    /**
     * 查询所有权限, 给角色分配权限时调用
     */
    fun listAllPermission(sysUser: SysUser): JSONObject

    /**
     * 添加角色
     */
    fun addRole(jsonObject: JSONObject): JSONObject

    /**
     * 修改角色
     */
    fun updateRole(jsonObject: JSONObject): JSONObject

    /**
     * 删除角色
     */
    fun deleteRole(jsonObject: JSONObject): JSONObject
}