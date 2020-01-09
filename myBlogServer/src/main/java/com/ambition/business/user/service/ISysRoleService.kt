package com.ambition.business.user.service

import com.ambition.business.user.domain.SysRole
import com.ambition.common.util.R
import com.baomidou.mybatisplus.extension.service.IService

/**
 * <pre>
 *
 *
 * @title: ISysRoleService
 * @description:
 * @company: 润投科技
 * @author: wuys
 * @datetime: 2019-12-18 16:16
 * </pre>
 */
interface ISysRoleService : IService<SysRole> {

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
    fun saveSysRole(entity: SysRole): R

    fun updateSysRoleById(entity: SysRole): R
}