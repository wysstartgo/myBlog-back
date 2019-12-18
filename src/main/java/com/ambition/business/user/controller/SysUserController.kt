package com.ambition.business.user.controller

import com.alibaba.fastjson.JSONObject
import com.ambition.business.user.converter.SysUserConverter
import com.ambition.business.user.domain.SysUser
import com.ambition.business.user.service.ISysUserService
import com.ambition.business.user.vo.SysUserVo
import com.ambition.common.annotations.AddSysLog
import com.ambition.common.annotations.CurrentUser
import com.ambition.common.annotations.LoginedUser
import com.ambition.common.enums.ErrorEnum
import com.ambition.common.util.CommonUtil
import com.ambition.common.util.R
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.apache.shiro.authz.annotation.Logical
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

/**
 * <pre>
 *
 *
 * @title: SysUserController
 * @description:
 * @company: 润投科技
 * @author: wuys
 * @datetime: 2019-12-18 17:42
 * </pre>
 */
@RestController
@RequestMapping("/user")
@Api(tags = ["用户管理"])
class SysUserController(@Autowired private val userService: ISysUserService) {


    /**
     * 查询用户列表
     */
    @RequiresPermissions("user:list")
    @GetMapping("/list")
    @AddSysLog(descrption = "查询用户列表")
    @LoginedUser
    @ApiOperation(value = "查询所有用户列表", notes = "查询所有用户列表")
    fun listUser(@CurrentUser sysUser: SysUser, request: HttpServletRequest): JSONObject {
        val jsonObject = CommonUtil.request2Json(request)
        jsonObject["groupId"] = sysUser.groupId
        return userService.listUser(jsonObject)
    }

    @RequiresPermissions("user:add")
    @PostMapping("/addUser")
    @AddSysLog(descrption = "添加用户")
    @LoginedUser
    @ApiOperation(value = "添加用户", notes = "添加用户相关接口")
    fun addUser(@CurrentUser loginUser: SysUser, @RequestBody sysUserVo: SysUserVo): R {
        //创建用户时需要传入店铺id
        if (sysUserVo.username == null || sysUserVo.password == null || sysUserVo.roleId == null) {
            return R.error(ErrorEnum.E_90003.errorCode, ErrorEnum.E_90003.errorMsg)
        }
        val sysUser = SysUserConverter.INSTANCE.convertFromVo(sysUserVo)
        sysUser.createUserId = loginUser.id
        sysUser.groupId = loginUser.groupId
        sysUser.groupName = loginUser.groupName
        sysUser.createUserName = loginUser.username
        return R.ok(userService.addUser(sysUser))
    }

    @RequiresPermissions("user:update")
    @PostMapping("/updateUser")
    @AddSysLog(descrption = "更新用户")
    @LoginedUser
    @ApiOperation(value = "更新用户", notes = "更新用户相关接口")
    fun updateUser(@CurrentUser loginUser: SysUser, @RequestBody sysUserVo: SysUserVo): R {
        if (sysUserVo.nickname == null || sysUserVo.roleId == null || sysUserVo.deleteStatus == null || sysUserVo.userId == null) {
            return R.error(ErrorEnum.E_90003.errorCode, ErrorEnum.E_90003.errorMsg)
        }
        //CommonUtil.hasAllRequired(requestJson, " nickname,roleId,deleteStatus, userId");
        val sysUser = SysUserConverter.INSTANCE.convertFromVo(sysUserVo)
        //基础框架留下的坑，需要修改
        sysUser.id = sysUserVo.userId
        sysUser.updateTime = Date()
        sysUser.updateUserId = loginUser.id
        sysUser.updateUserName = loginUser.username
        return R.ok(userService.updateUser(sysUser))
    }

    @RequiresPermissions(value = ["user:add", "user:update"], logical = Logical.OR)
    @GetMapping("/getAllRoles")
    @AddSysLog(descrption = "获取所有权限")
    @LoginedUser
    @ApiOperation(value = "获取所有权限", notes = "获取所有权限")
    fun getAllRoles(@CurrentUser @Valid loginUser: SysUser): JSONObject {
        return userService.getAllRoles(loginUser)
    }

    /**
     * 角色列表
     */
    @RequiresPermissions("role:list")
    @GetMapping("/listRole")
    @AddSysLog(descrption = "获取角色列表")
    @LoginedUser
    @ApiOperation(value = "获取角色列表", notes = "获取角色列表")
    fun listRole(@CurrentUser loginUser: SysUser): JSONObject {
        return userService.listRole(loginUser)
    }

    /**
     * 查询所有权限, 给角色分配权限时调用
     */
    @RequiresPermissions("role:list")
    @GetMapping("/listAllPermission")
    @AddSysLog(descrption = "查询所有权限")
    @LoginedUser
    @ApiOperation(value = "查询所有权限", notes = "查询所有权限")
    fun listAllPermission(@CurrentUser loginUser: SysUser): JSONObject {
        return userService.listAllPermission(loginUser)
    }

    /**
     * 新增角色
     */
    @RequiresPermissions("role:add")
    @PostMapping("/addRole")
    @AddSysLog(descrption = "新增角色")
    @LoginedUser
    @ApiOperation(value = "新增角色", notes = "新增角色")
    fun addRole(@CurrentUser loginUser: SysUser, @RequestBody requestJson: JSONObject): JSONObject {
        CommonUtil.hasAllRequired(requestJson, "roleName,permissions")
        requestJson["createUserId"] = loginUser.id
        requestJson["createUserName"] = loginUser.username
        requestJson["groupId"] = loginUser.groupId
        requestJson["groupName"] = loginUser.groupName
        return userService.addRole(requestJson)
    }

    /**
     * 修改角色
     */
    @RequiresPermissions("role:update")
    @PostMapping("/updateRole")
    @AddSysLog(descrption = "修改角色")
    @LoginedUser
    @ApiOperation(value = "修改角色", notes = "修改角色")
    fun updateRole(@RequestBody requestJson: JSONObject): JSONObject {
        CommonUtil.hasAllRequired(requestJson, "roleId,roleName,permissions")
        return userService.updateRole(requestJson)
    }

    /**
     * 删除角色
     */
    @RequiresPermissions("role:delete")
    @PostMapping("/deleteRole")
    @AddSysLog(descrption = "删除角色")
    @LoginedUser
    @ApiOperation(value = "删除角色", notes = "删除角色")
    fun deleteRole(@RequestBody requestJson: JSONObject): JSONObject {
        CommonUtil.hasAllRequired(requestJson, "roleId")
        return userService.deleteRole(requestJson)
    }
}