package com.ambition.business.user.controller

import com.alibaba.fastjson.JSONObject
import com.ambition.business.user.domain.SysUser
import com.ambition.business.user.service.ILoginService
import com.ambition.common.annotations.AddSysLog
import com.ambition.common.annotations.CurrentUser
import com.ambition.common.annotations.LoginedUser
import com.ambition.common.util.CommonUtil
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


/**
 * <pre>
 *
 *
 * @title: LoginController
 * @description:
 * @author: wuys
 * @datetime: 2019-12-26 15:38
 * </pre>
 */
@RestController
@RequestMapping("/login")
@Api(tags = ["登录管理"])
class LoginController {

    @Autowired
    private val loginService: ILoginService? = null

    /**
     * 查询当前登录用户的信息
     */
    @PostMapping("/getInfo")
    @AddSysLog(descrption = "获取当前登录的用户信息")
    @LoginedUser
    @ApiOperation(value = "获取登录用户信息", notes = "获取登录用户信息")
    fun getUserInfo(@CurrentUser sysUser:SysUser,request: HttpServletRequest, response: HttpServletResponse): JSONObject = loginService!!.getUserInfo(sysUser,request, response)

    /**
     * 登录
     */
    @PostMapping("/auth")
    @AddSysLog(descrption = "用户登录")
    @ApiOperation(value = "登录接口", notes = "用户登录")
    fun authLogin(@RequestBody requestJson: JSONObject, request: HttpServletRequest, response: HttpServletResponse): JSONObject {
        CommonUtil.hasAllRequired(requestJson, "username,password")
        return loginService!!.authLogin(requestJson,request,response)
    }

    /**
     * 登出
     */
    @PostMapping("/logout")
    @AddSysLog(descrption = "用户登出")
    @ApiOperation(value = "登出接口", notes = "用户登出")
    fun logout(request: HttpServletRequest, response: HttpServletResponse): JSONObject {
        return loginService!!.logout(request, response)
    }
}