package com.ambition.business.user.service.impl

import com.alibaba.fastjson.JSONObject
import com.ambition.business.user.domain.SysGroup
import com.ambition.business.user.mapper.SysGroupMapper
import com.ambition.business.user.service.ISysGroupService
import com.ambition.common.constants.Constants
import com.ambition.common.util.R
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * <pre>
 *
 *
 * @title: SysGroupServiceImpl
 * @description:
 * @company: 润投科技
 * @author: wuys
 * @datetime: 2019-12-18 15:51
 * </pre>
 */
@Service
open class SysGroupServiceImpl : ServiceImpl<SysGroupMapper, SysGroup>(),ISysGroupService {

    @Transactional
    override fun getAllGroups(): List<JSONObject> {
        return baseMapper.getAllGroups()
    }

    @Transactional
    override fun findListByPage(page: Int, pageSize: Int, groupId: Long?): R {
        var pageSize = pageSize
        if (pageSize == 0) {
            pageSize = Constants.DEFAULT_PAGESIZE
        }
        val dictPage = Page<SysGroup>(page.toLong(), pageSize.toLong())
        var sysDictIPage: IPage<SysGroup>? = null
        val lambdaQueryWrapper = Wrappers.lambdaQuery<SysGroup>()
        sysDictIPage = baseMapper.selectPage(dictPage, lambdaQueryWrapper.select(SysGroup::class.java) { i -> true })
        return R.ok(sysDictIPage)
    }

    override fun deleteByIds(ids: List<Long>): R {
        val result = this.baseMapper.deleteBatchIds(ids)
        return if (result > 0) {
            R.ok()
        } else R.error()
    }

    override fun getById(id: Long?): R {
        val e = super.getById(id)
        return R.ok(e)
    }

    override fun saveSysGroup(entity: SysGroup): R {
        val r = this.save(entity)
        return if (r) {
            R.ok()
        } else R.error()
    }

    override fun updateSysGroupById(entity: SysGroup): R {
        val r = this.updateById(entity)
        return if (r) {
            R.ok()
        } else R.error()
    }
}