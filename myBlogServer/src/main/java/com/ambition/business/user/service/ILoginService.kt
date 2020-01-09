package com.ambition.business.user.service

import com.alibaba.fastjson.JSONObject
import com.ambition.business.user.domain.SysUser
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


/**
 * <pre>
 *
 *
 * @title: ILoginService
 * @description:
 * @author: wuys
 * @datetime: 2019-12-26 15:39
 * </pre>
 */
interface ILoginService {

    /**
     * 查询当前登录用户的权限等信息
     */
    fun getUserInfo(sysUser:SysUser,request: HttpServletRequest, response: HttpServletResponse): JSONObject

    /**
     * 登录表单提交
     */
    fun authLogin(jsonObject: JSONObject,request: HttpServletRequest, response: HttpServletResponse): JSONObject

    /**
     * 根据用户名和密码查询对应的用户
     *
     * @param username 用户名
     * @param password 密码
     */
    fun getUser(username: String, password: String): SysUser?

    /**
     * 退出登录
     */
    fun logout(request: HttpServletRequest, response: HttpServletResponse): JSONObject
}
