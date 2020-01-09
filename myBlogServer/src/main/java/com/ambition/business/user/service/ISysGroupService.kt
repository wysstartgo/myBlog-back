package com.ambition.business.user.service

import com.alibaba.fastjson.JSONObject
import com.ambition.business.user.domain.SysGroup
import com.ambition.common.util.R
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.service.IService

/**
 * <pre>
 *
 *
 * @title: ISysGroupService
 * @description:
 * @company: 润投科技
 * @author: wuys
 * @datetime: 2019-12-18 15:50
 * </pre>
 */
interface ISysGroupService : IService<SysGroup> {

    /**
     * 获取所有组织
     */
    fun getAllGroups(): List<JSONObject>

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
    fun saveSysGroup(entity: SysGroup): R

    fun updateSysGroupById(entity: SysGroup): R

    /**
     * 查询列表
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    fun selectSysGroupList(page: Int?, pageSize: Int?, name: String?): IPage<SysGroup>

    /**
     * 查询店铺列表
     * @return
     */
    fun getGroupList(): List<JSONObject>

    fun getSysGroupList(): List<SysGroup>

}