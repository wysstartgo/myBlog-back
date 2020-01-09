package com.ambition.business.user.controller

import com.ambition.business.user.service.ISysGroupService
import com.ambition.common.util.R
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.springframework.context.annotation.Lazy
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.Resource


/**
 * <pre>
 *
 *
 * @title: TestController
 * @description:
 * @author: wuys
 * @datetime: 2019-12-31 14:30
 * </pre>
 */
@RestController
@RequestMapping("/test")
open class TestController {


    @Resource
    @Lazy
    lateinit var sysGroupService : ISysGroupService



    @GetMapping("/list")
    @RequiresPermissions("group:add")
    fun test(): R {
        println("--------------------")
        println(sysGroupService)
        println("--------------------")
        return R.ok()
    }
}