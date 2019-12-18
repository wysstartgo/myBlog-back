package com.ambition.business.user.mapper

import com.alibaba.fastjson.JSONObject
import com.ambition.business.user.domain.SysGroup
import com.baomidou.mybatisplus.core.mapper.BaseMapper

/**
 * <pre>
 *
 *
 * @title: SysGroupMapper
 * @description:
 * @company: 润投科技
 * @author: wuys
 * @datetime: 2019-12-18 15:48
 * </pre>
 */
interface SysGroupMapper : BaseMapper<SysGroup> {

    fun getAllGroups(): List<JSONObject>
}