package com.ambition.business.user.service.impl

import com.alibaba.fastjson.JSONObject
import com.ambition.business.user.domain.SysUser
import com.ambition.business.user.mapper.SysUserMapper
import com.ambition.business.user.service.ICacheService
import com.ambition.business.user.service.ISysRolePermissionService
import com.ambition.business.user.service.ISysUserService
import com.ambition.common.constants.Constants
import com.ambition.common.enums.ErrorEnum
import com.ambition.common.util.CommonUtil
import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.core.toolkit.support.SFunction
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.annotation.Resource


/**
 * <pre>
 *
 *
 * @title: SysUserServiceImpl
 * @description:
 * @author: wuys
 * @datetime: 2019-12-18 13:45
 * </pre>
 */
@Service
open class SysUserServiceImpl : ServiceImpl<SysUserMapper, SysUser>(), ISysUserService {

    @Resource
    private val cacheService: ICacheService? = null

    @Resource
    private val sysRolePermissionService: ISysRolePermissionService? = null


    override fun findUserByUsernameAndPassword(username: String, password: String): SysUser? {
        return baseMapper.selectOne(Wrappers.query<SysUser>().eq("username", username).eq("password", password))
    }

    /**
     * 不能用page分类，因为有表关联查询(@TODO 看是否可以使用冗余字段去掉join)
     * 用户列表
     */
    override fun listUser(jsonObject: JSONObject): JSONObject {
        CommonUtil.fillPageParam(jsonObject)
        val count = baseMapper.countUser(jsonObject)
        val list = baseMapper.listUser(jsonObject)
        return CommonUtil.successPage(jsonObject, list, count)
    }


    /**
     * 添加用户
     */
    override fun addUser(sysUser: SysUser): JSONObject {
        val exist = baseMapper.selectCount(Wrappers.lambdaQuery<SysUser>().eq(SFunction<SysUser, Any> { SysUser::username }, sysUser.username))
        if (exist > 0) {
            return CommonUtil.errorJson(ErrorEnum.E_10009)
        }
        val groupId = sysUser.groupId
        val objects = cacheService!!.groupCacheList()
        var groupName = ""
        for (j in objects) {
            if (j.getLong("groupId") == groupId) {
                groupName = j.getString("groupName")
                break
            }
        }
        sysUser.groupName = groupName
        baseMapper.insert(sysUser)
        return CommonUtil.successJson()
    }

    /**
     * 查询所有的角色
     * 在添加/修改用户的时候要使用此方法
     */
    override fun getAllRoles(sysUser: SysUser): JSONObject {
        val roles = baseMapper.getAllRoles(sysUser)
        return CommonUtil.successPage(roles)
    }

    /**
     * 修改用户
     */
    override fun updateUser(sysUser: SysUser): Boolean {
        val updateUser = baseMapper.updateUser(sysUser)
        return updateUser > 0
    }

    /**
     * 角色列表
     */
    override fun listRole(sysUser: SysUser): JSONObject {
        val roles = baseMapper.listRole(sysUser)
        return CommonUtil.successPage(roles)
    }

    /**
     * 查询所有权限, 给角色分配权限时调用
     */
    override fun listAllPermission(sysUser: SysUser): JSONObject {
        if (sysUser.roleId === Constants.SYS_ID.toInt()) {
            //系统管理员或店铺管理员
            val permissions = baseMapper.listAllPermission()
            return CommonUtil.successPage(permissions)
        } else {
            //否则的话去查询该用户有的菜单权限
            val permissions = sysRolePermissionService!!.getPermissionsByRoleId(sysUser.roleId)
            return CommonUtil.successPage(baseMapper.getUserPermissions(permissions))
        }
    }

    /**
     * 添加角色
     */
    @Transactional(rollbackFor = [Exception::class])
    override fun addRole(jsonObject: JSONObject): JSONObject {
        baseMapper.insertRole(jsonObject)
        baseMapper.insertRolePermission(jsonObject.getString("roleId"), jsonObject["permissions"] as List<Int>)
        return CommonUtil.successJson()
    }

    /**
     * 修改角色
     */
    @Transactional(rollbackFor = [Exception::class])
    override fun updateRole(jsonObject: JSONObject): JSONObject {
        val roleId = jsonObject.getString("roleId")
        val newPerms = jsonObject["permissions"] as List<Int>
        val roleInfo = baseMapper.getRoleAllInfo(jsonObject)
        val oldPerms = roleInfo["permissionIds"] as Set<Int>
        //修改角色名称
        dealRoleName(jsonObject, roleInfo)
        //添加新权限
        saveNewPermission(roleId, newPerms, oldPerms)
        //移除旧的不再拥有的权限
        removeOldPermission(roleId, newPerms, oldPerms)
        return CommonUtil.successJson()
    }

    /**
     * 修改角色名称
     */
    private fun dealRoleName(paramJson: JSONObject, roleInfo: JSONObject) {
        val roleName = paramJson.getString("roleName")
        if (roleName != roleInfo.getString("roleName")) {
            baseMapper.updateRoleName(paramJson)
        }
    }

    /**
     * 为角色添加新权限
     */
    private fun saveNewPermission(roleId: String, newPerms: Collection<Int>, oldPerms: Collection<Int>) {
        val waitInsert = ArrayList<Int>()
        for (newPerm in newPerms) {
            if (!oldPerms.contains(newPerm)) {
                waitInsert.add(newPerm)
            }
        }
        if (waitInsert.size > 0) {
            baseMapper.insertRolePermission(roleId, waitInsert)
        }
    }

    /**
     * 删除角色 旧的 不再拥有的权限
     */
    private fun removeOldPermission(roleId: String, newPerms: Collection<Int>, oldPerms: Collection<Int>) {
        val waitRemove = ArrayList<Int>()
        for (oldPerm in oldPerms) {
            if (!newPerms.contains(oldPerm)) {
                waitRemove.add(oldPerm)
            }
        }
        if (waitRemove.size > 0) {
            baseMapper.removeOldPermission(roleId, waitRemove)
        }
    }

    /**
     * 删除角色
     */
    @Transactional(rollbackFor = [Exception::class])
    override fun deleteRole(jsonObject: JSONObject): JSONObject {
        val roleInfo = baseMapper.getRoleAllInfo(jsonObject)
        val users = roleInfo["users"] as List<JSONObject>
        if (users != null && users.size > 0) {
            return CommonUtil.errorJson(ErrorEnum.E_10008)
        }
        baseMapper.removeRole(jsonObject)
        baseMapper.removeRoleAllPermission(jsonObject)
        return CommonUtil.successJson()
    }

}