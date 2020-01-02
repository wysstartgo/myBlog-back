package com.ambition.business.user.service.impl

import com.ambition.business.user.domain.SysRole
import com.ambition.business.user.mapper.SysRoleMapper
import com.ambition.business.user.service.ISysRoleService
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
 * @title: SysRoleServiceImpl
 * @description:
 * @company: 润投科技
 * @author: wuys
 * @datetime: 2019-12-18 16:17
 * </pre>
 */
@Service
open class SysRoleServiceImpl : ServiceImpl<SysRoleMapper, SysRole>(), ISysRoleService {

    @Transactional
    override fun findListByPage(page: Int, pageSize: Int, groupId: Long?): R {
        var pageSize = pageSize
        if (pageSize == 0) {
            pageSize = Constants.DEFAULT_PAGESIZE
        }
        val dictPage = Page<SysRole>(page.toLong(), pageSize.toLong())
        var sysDictIPage: IPage<SysRole>? = null
        val lambdaQueryWrapper = Wrappers.query<SysRole>()
        sysDictIPage = baseMapper.selectPage(dictPage, lambdaQueryWrapper.select(SysRole::class.java) { i -> true })
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

    override fun saveSysRole(entity: SysRole): R {
        val r = this.save(entity)
        return if (r) {
            R.ok()
        } else R.error()
    }

    override fun updateSysRoleById(entity: SysRole): R {
        val r = this.updateById(entity)
        return if (r) {
            R.ok()
        } else R.error()
    }
}