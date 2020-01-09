package com.ambition.business.user.controller

import com.ambition.business.user.domain.SysGroup
import com.ambition.business.user.domain.SysUser
import com.ambition.business.user.service.ISysGroupService
import com.ambition.common.annotations.AddSysLog
import com.ambition.common.annotations.CurrentUser
import com.ambition.common.annotations.LoginedUser
import com.ambition.common.constants.Constants
import com.ambition.common.enums.YesOrNoEnum
import com.ambition.common.util.R
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore

/**
 * <pre>
 *
 *
 * @title: SysGroupController
 * @description:
 * @company: 润投科技
 * @author: wuys
 * @datetime: 2019-12-18 17:55
 * </pre>
 */
@RestController
@RequestMapping("/group")
@Api(tags = ["组织管理"])
open class SysGroupController {

    @Autowired
    private val sysGroupService: ISysGroupService? = null

    @PostMapping("/save")
    @LoginedUser
    @AddSysLog(descrption = "添加组织")
    @RequiresPermissions("group:add")
    @ApiOperation(value = "添加组织", notes = "添加组织")
    fun save(@CurrentUser @ApiIgnore sysUser: SysUser, @RequestBody sysGroup: SysGroup): R {
        return R.ok(sysGroupService!!.saveSysGroup(sysGroup))
    }

    @PostMapping("/update")
    @AddSysLog(descrption = "更新组织")
    @LoginedUser
    @RequiresPermissions("group:update")
    @ApiOperation(value = "更新组织", notes = "更新组织")
    fun update(@CurrentUser @ApiIgnore sysUser: SysUser, @RequestBody sysGroup: SysGroup): R {
        return R.ok(sysGroupService!!.updateSysGroupById(sysGroup))
    }

    @GetMapping("/list")
    @AddSysLog(descrption = "查询组织列表")
    @LoginedUser
    @RequiresPermissions("group:list")
    @ApiOperation(value = "分页查询组织列表", notes = "分页查询组织列表")
    fun selectList(@RequestParam(required = false) type: Int?, @RequestParam(required = false) page: Int?, @RequestParam(required = false) pageSize: Int?, @RequestParam(required = false) name: String?): R {
        var page = page
        var pageSize = pageSize
        return if (YesOrNoEnum.YES.value == type) {
            R.ok(sysGroupService!!.getAllGroups())
        } else {
            if (page == null) {
                page = 1
            }
            if (pageSize == null) {
                pageSize = Constants.DEFAULT_PAGESIZE
            }
            val list = sysGroupService!!.selectSysGroupList(page, pageSize, name)
            //println(JSONObject.toJSONString(list))
            R.ok(list)
        }
    }


    @GetMapping("/forbidden")
    @AddSysLog(descrption = "禁用组织")
    @LoginedUser
    @RequiresPermissions("group:forbidden")
    @ApiOperation(value = "禁用组织", notes = "禁用组织")
    fun forbidden(@CurrentUser @ApiIgnore sysUser: SysUser, @RequestBody sysGroup: SysGroup): R {
        sysGroup.setIsValid(YesOrNoEnum.NO.value)
        return R.ok(sysGroupService!!.updateSysGroupById(sysGroup))
    }

    @GetMapping("/open")
    @AddSysLog(descrption = "启用组织")
    @LoginedUser
    @RequiresPermissions("group:open")
    @ApiOperation(value = "启用组织", notes = "启用组织")
    fun open(@CurrentUser @ApiIgnore sysUser: SysUser, @RequestBody sysGroup: SysGroup): R {
        return R.ok(sysGroupService!!.updateSysGroupById(sysGroup))
    }
}