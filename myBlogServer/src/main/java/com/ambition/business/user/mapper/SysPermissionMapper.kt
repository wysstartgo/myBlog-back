package com.ambition.business.user.mapper

import com.alibaba.fastjson.JSONObject
import com.ambition.business.user.domain.SysPermission
import com.baomidou.mybatisplus.core.mapper.BaseMapper

/**
 * <pre>
 *
 *
 * @title: SysPermissionMapper
 * @description:
 * @author: wuys
 * @datetime: 2019-12-18 11:14
 * </pre>
 */
interface SysPermissionMapper : BaseMapper<SysPermission> {

    /**
     * 查询用户的角色 菜单 权限
     */
    fun getUserPermission(id: Long): JSONObject

    /**
     * 查询所有的菜单
     */
    fun getAllMenu(): Set<String>

    /**
     * 查询所有的权限
     */
    fun getAllPermission(): Set<String>

    /**
     * 通过userId获取用户权限
     */
    fun getUserPermissionByRoleId(roleId: Int): List<SysPermission>

    /**
     * 所有的权限信息
     */
    fun listAllPermission(): JSONObject

    /**
     * 获取用户权限
     */
    fun getUserPermissions(permids: String): JSONObject
}