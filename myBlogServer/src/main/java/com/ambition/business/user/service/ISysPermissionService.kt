package com.ambition.business.user.service

import com.alibaba.fastjson.JSONObject
import com.ambition.business.user.domain.SysPermission
import com.ambition.business.user.domain.SysUser
import com.ambition.common.util.R
import com.baomidou.mybatisplus.extension.service.IService

/**
 * <pre>
 *
 *
 * @title: ISysPermissionService
 * @description:
 * @company: 润投科技
 * @author: wuys
 * @datetime: 2019-12-18 16:22
 * </pre>
 */
interface ISysPermissionService : IService<SysPermission> {

    /**
     * 查询某用户的 角色  菜单列表   权限列表
     */
    fun getUserPermission(userId: Long): JSONObject

    /**
     * 分页查询
     */
    fun findListByPage(page: Int, pageSize: Int, groupId: Long?): R

    /**
     * 删除多条
     */
    fun deleteByIds(ids: List<Long>): R

    /**
     * 通过id查询
     */
    fun getById(id: Long?): R

    /**
     * 保存
     */
    fun saveSysPermission(entity: SysPermission): R

    fun updateSysPermissionById(entity: SysPermission): R


    fun findAllSysPermission(): List<SysPermission>

    fun findSysPermissionByLoginUser(sysUser: SysUser): List<SysPermission>

}