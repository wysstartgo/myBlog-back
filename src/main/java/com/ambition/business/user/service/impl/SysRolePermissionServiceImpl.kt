package com.ambition.business.user.service.impl

import com.ambition.business.user.domain.SysRolePermission
import com.ambition.business.user.mapper.SysRolePermissionMapper
import com.ambition.business.user.service.ISysRolePermissionService
import com.ambition.common.constants.Constants
import com.ambition.common.util.R
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.core.toolkit.support.SFunction
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors

/**
 * <pre>
 *
 *
 * @title: SysRolePermissionServiceImpl
 * @description:
 * @company: 润投科技
 * @author: wuys
 * @datetime: 2019-12-18 16:09
 * </pre>
 */
@Service
open class SysRolePermissionServiceImpl : ServiceImpl<SysRolePermissionMapper, SysRolePermission>(), ISysRolePermissionService {

    override fun getPermissionsByRoleId(roleId: Int?): List<Int> {
        val sysRolePermissions = baseMapper.selectList(Wrappers.lambdaQuery<SysRolePermission>().eq(SFunction<SysRolePermission, Any> { SysRolePermission::roleId }, roleId))
        return sysRolePermissions.stream().map { sysRolePermission -> sysRolePermission.permissionId }
                .collect(Collectors.toList<Int>())
    }

    @Transactional
    override fun findListByPage(page: Int, pageSize: Int, groupId: Long?): R {
        var pageSize = pageSize
        if (pageSize == 0) {
            pageSize = Constants.DEFAULT_PAGESIZE
        }
        val dictPage = Page<SysRolePermission>(page.toLong(), pageSize.toLong())
        var sysDictIPage: IPage<SysRolePermission>? = null
        val lambdaQueryWrapper = Wrappers.lambdaQuery<SysRolePermission>()
        sysDictIPage = baseMapper.selectPage(dictPage, lambdaQueryWrapper.select(SysRolePermission::class.java) { i -> true })
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

    override fun saveSysRolePermission(entity: SysRolePermission): R {
        val r = this.save(entity)
        return if (r) {
            R.ok()
        } else R.error()
    }

    override fun updateSysRolePermissionById(entity: SysRolePermission): R {
        val r = this.updateById(entity)
        return if (r) {
            R.ok()
        } else R.error()
    }

}