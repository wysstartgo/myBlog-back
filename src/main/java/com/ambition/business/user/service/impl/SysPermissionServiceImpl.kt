package com.ambition.business.user.service.impl

import com.alibaba.fastjson.JSONObject
import com.ambition.business.user.domain.SysPermission
import com.ambition.business.user.mapper.SysPermissionMapper
import com.ambition.business.user.service.ISysPermissionService
import com.ambition.common.constants.Constants
import com.ambition.common.util.R
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


/**
 * <pre>
 *
 *
 * @title: SysPermissionServiceImpl
 * @description:
 * @company: 润投科技
 * @author: wuys
 * @datetime: 2019-12-18 16:23
 * </pre>
 */
@Service
open class SysPermissionServiceImpl : ServiceImpl<SysPermissionMapper, SysPermission>(), ISysPermissionService {

    @Autowired
    private val permissionMapper: SysPermissionMapper? = null


    /**
     * 查询某用户的 角色  菜单列表   权限列表
     */
    override fun getUserPermission(username: String): JSONObject {
        val userPermission = getUserPermissionFromDB(username)
        return userPermission
    }

    /**
     * 从数据库查询用户权限信息
     */
    private fun getUserPermissionFromDB(username: String): JSONObject {
        val userPermission = permissionMapper!!.getUserPermission(username)
        //管理员角色ID为1
        val adminRoleId = 1
        //如果是管理员
        val roleIdKey = "roleId"
        if (adminRoleId == userPermission.getIntValue(roleIdKey)) {
            //查询所有菜单  所有权限
            val menuList = permissionMapper.getAllMenu()
            val permissionList = permissionMapper.getAllPermission()
            userPermission["menuList"] = menuList
            userPermission["permissionList"] = permissionList
        }
        return userPermission
    }

    @Transactional
    override fun findListByPage(page: Int, pageSize: Int, groupId: Long?): R {
        var pageSize = pageSize
        if (pageSize == 0) {
            pageSize = Constants.DEFAULT_PAGESIZE
        }
        val dictPage = Page<SysPermission>(page.toLong(), pageSize.toLong())
        var sysDictIPage: IPage<SysPermission>? = null
        val lambdaQueryWrapper = Wrappers.query<SysPermission>()
        sysDictIPage = baseMapper.selectPage(dictPage, lambdaQueryWrapper.select(SysPermission::class.java) { i -> true })
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

    override fun saveSysPermission(entity: SysPermission): R {
        val r = this.save(entity)
        return if (r) {
            R.ok()
        } else R.error()
    }

    override fun updateSysPermissionById(entity: SysPermission): R {
        val r = this.updateById(entity)
        return if (r) {
            R.ok()
        } else R.error()
    }

}
