package com.ambition.business.user.service.impl

import com.alibaba.fastjson.JSONObject
import com.ambition.business.user.domain.PermissionTree
import com.ambition.business.user.domain.SysPermission
import com.ambition.business.user.domain.SysUser
import com.ambition.business.user.mapper.SysUserMapper
import com.ambition.business.user.service.ICacheService
import com.ambition.business.user.service.ISysPermissionService
import com.ambition.business.user.service.ISysRolePermissionService
import com.ambition.business.user.service.ISysUserService
import com.ambition.common.constants.Constants
import com.ambition.common.enums.ErrorEnum
import com.ambition.common.enums.ParentChildEnum
import com.ambition.common.util.CommonUtil
import com.ambition.common.util.R
import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.google.common.collect.Lists
import org.apache.commons.collections.CollectionUtils
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

    companion object {
        val ROOT_ID: Int = 1
        val HAS_CHILD = 9
    }

    @Resource
    private val cacheService: ICacheService? = null

    @Resource
    private val sysRolePermissionService: ISysRolePermissionService? = null

    @Resource
    private val sysPermissionService: ISysPermissionService? = null


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
        val exist = baseMapper.selectCount(Wrappers.query<SysUser>().eq("username", sysUser.username))
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
    override fun listAllPermission(sysUser: SysUser): R {
        var permissionList: List<SysPermission>? = null
        if (sysUser.roleId === Constants.SYS_ID.toInt()) {
            //系统管理员或管理员
            permissionList = sysPermissionService!!.findAllSysPermission()
        } else {
            //否则的话去查询该用户有的菜单权限
            permissionList = sysPermissionService!!.findSysPermissionByLoginUser(sysUser)
        }
        val groups = permissionList.groupBy { it.menuName }
        //树的根部  一组构建一个Node
        val lists: MutableList<PermissionTree> = Lists.newArrayListWithCapacity(groups!!.size)
        groups.keys.forEach { buildTreeNode(groups.getValue(it))?.let { it1 -> lists.add(it1) } }
//            val permissions = baseMapper.listAllPermission()
        return R.ok(lists)
    }

    /**
     * 构建树节点 两层
     */
    fun buildTreeNode(lists: List<SysPermission>): PermissionTree? {
        val topNode = PermissionTree()
        val subMaps = lists.groupBy { it.pid }
        val rootNodes = subMaps[ROOT_ID]
        var rootNode: SysPermission? = null
        if (CollectionUtils.isEmpty(rootNodes)) {
            //根据子结点创建一个rootNodes
            //找到requirePermission为1的那个
            val firstMatch = lists.stream().filter { Constants.REQUIRED == it.requiredPermission }.findFirst()
            if (firstMatch.isPresent) {
                val firstPerm = firstMatch.get()
                val rootPerm = SysPermission()
                rootPerm.menuName = firstPerm.menuName
                rootPerm.pid = Constants.SYS_ID.toInt()
                rootPerm.id = firstPerm.pid
                rootPerm.children = ParentChildEnum.Parent.code
                rootPerm.permissionCode = firstPerm.permissionCode
                rootPerm.menuCode = firstPerm.menuCode
                rootPerm.permissionName = firstPerm.menuName
                rootPerm.requiredPermission = Constants.NOT_REQUIRED
                rootNode = rootPerm
            } else {
                //需要去查出父节点
                val firstPerm = lists[0]
                val pid = firstPerm.pid
                rootNode = sysPermissionService!!.getById(pid)
            }
        } else {
            //一般只有一个根节点，对应的是第一个
            rootNode = rootNodes!![0]
        }

        topNode.id = rootNode!!.id
        topNode.pid = rootNode.pid
        topNode.menuName = rootNode.menuName
        //然后获取非根节点
        val childNodes = subMaps[rootNode.id]
        //遍历节点
        if (childNodes != null) {
            for (childNode in childNodes) {
                //第二层
                val topChildNode = PermissionTree()
                topChildNode.pid = childNode.pid
                topChildNode.id = childNode.id
                if (Constants.REQUIRED == childNode.requiredPermission) {
                    topChildNode.menuName = childNode.permissionName + "(必选)"
                } else {
                    topChildNode.menuName = childNode.permissionName
                }
                if (topNode.children == null) {
                    topNode.children = Lists.newArrayListWithCapacity(childNodes.size)
                }
                topNode.children!!.add(topChildNode)
            }
        }
        return topNode
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
        if (users != null && users.isNotEmpty()) {
            return CommonUtil.errorJson(ErrorEnum.E_10008)
        }
        baseMapper.removeRole(jsonObject)
        baseMapper.removeRoleAllPermission(jsonObject)
        return CommonUtil.successJson()
    }

}