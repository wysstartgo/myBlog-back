package com.ambition.business.user.mapper

import com.alibaba.fastjson.JSONObject
import com.ambition.business.user.domain.SysUser
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Param

/**
 * <pre>
 *
 *
 * @title: SysUserMapper
 * @description:
 * @company: 润投科技
 * @author: wuys
 * @datetime: 2019-12-18 11:50
 * </pre>
 */
interface SysUserMapper : BaseMapper<SysUser> {
    /**
     * 查询用户数量
     */
    fun countUser(jsonObject: JSONObject): Int

    /**
     * 查询用户列表
     */
    fun listUser(jsonObject: JSONObject): List<JSONObject>

    /**
     * 查询所有的角色
     * 在添加/修改用户的时候要使用此方法
     */
    fun getAllRoles(sysUser: SysUser): List<JSONObject>

    /**
     * 校验用户名是否已存在
     */
    fun queryExistUsername(jsonObject: JSONObject): Int

    /**
     * 新增用户
     */
    fun addUser(jsonObject: JSONObject): Int

    /**
     * 修改用户
     */
    fun updateUser(sysUser: SysUser): Int

    /**
     * 角色列表
     */
    fun listRole(sysUser: SysUser): List<JSONObject>

    /**
     * 查询所有权限, 给角色分配权限时调用
     */
    fun listAllPermission(): List<JSONObject>

    /**
     * 获取用户的权限
     * @param permids
     * @return
     */
    fun getUserPermissions(@Param("permids") permids: List<Int>): List<JSONObject>

    /**
     * 新增角色
     */
    fun insertRole(jsonObject: JSONObject): Int

    /**
     * 批量插入角色的权限
     *
     * @param roleId      角色ID
     * @param permissions 权限
     */
    fun insertRolePermission(@Param("roleId") roleId: String, @Param("permissions") permissions: List<Int>): Int

    /**
     * 将角色曾经拥有而修改为不再拥有的权限 delete_status改为'2'
     */
    fun removeOldPermission(@Param("roleId") roleId: String, @Param("permissions") permissions: List<Int>): Int

    /**
     * 修改角色名称
     */
    fun updateRoleName(jsonObject: JSONObject): Int

    /**
     * 查询某角色的全部数据
     * 在删除和修改角色时调用
     */
    fun getRoleAllInfo(jsonObject: JSONObject): JSONObject

    /**
     * 删除角色
     */
    fun removeRole(jsonObject: JSONObject): Int

    /**
     * 删除本角色全部权限
     */
    fun removeRoleAllPermission(jsonObject: JSONObject): Int
}