package com.ambition.business.user.service.impl

import com.alibaba.fastjson.JSONObject
import com.ambition.business.user.domain.SysUser
import com.ambition.business.user.service.ILoginService
import com.ambition.business.user.service.ISysPermissionService
import com.ambition.business.user.service.ISysUserService
import com.ambition.common.constants.Constants
import com.ambition.common.util.CommonUtil
import com.ambition.common.util.CookieUtils
import com.ambition.common.util.JwtUtil
import com.ambition.common.util.RandomUtil
import com.ambition.config.shiro.StatelessToken
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.AuthenticationException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.annotation.Resource
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


/**
 * <pre>
 *
 *
 * @title: LoginServiceImpl
 * @description:
 * @author: wuys
 * @datetime: 2019-12-26 15:40
 * </pre>
 */
@Service
class LoginServiceImpl : ILoginService {

    @Resource
    private val userService: ISysUserService? = null

    @Autowired
    private val permissionService: ISysPermissionService? = null

    /**
     * 查询当前登录用户的权限等信息
     */
    override fun getUserInfo(sysUser:SysUser,request: HttpServletRequest, response: HttpServletResponse): JSONObject {
        val info = JSONObject()
        val perms = CookieUtils.readCookiePermValue(request)
        if(perms.isPresent){
            info["userPermission"] = perms.get()
            return CommonUtil.successJson(info)
        }
        val userPermission = permissionService!!.getUserPermission(sysUser!!.id!!)
        val permToken = JwtUtil.generatePermToken(userPermission)
        CookieUtils.addCookie(response,Constants.AUTH_PERM,permToken,"/",Constants.MAX_AGE,request.serverName,true)
        info["userPermission"] = userPermission
        return CommonUtil.successJson(info)
    }

    /**
     * 登录表单提交
     */
    override fun authLogin(jsonObject: JSONObject,request: HttpServletRequest,response: HttpServletResponse): JSONObject {
        val info = JSONObject()
        val username = jsonObject.getString("username")
        val password = jsonObject.getString("password")
        val sysUser = userService!!.findUserByUsernameAndPassword(username, password)
        if (sysUser == null) {
            info["result"] = "fail"
        } else {
//            val sysUserVo = SysUserConverter.INSTANCE.convertToVo(sysUser)
//            //密码置为null
//            sysUserVo.password = null
//            val content = Maps.newHashMap<String, Any>()
//            content["user"] = JSON.toJSONString(sysUserVo)
//            content["nonstr"] = Clock.systemUTC().millis()
            val generateToken = JwtUtil.generateToken(sysUser)
            val token = StatelessToken(sysUser.id.toString(),generateToken,Constants.SHIRO_SALT)
            val currentUser = SecurityUtils.getSubject()
            try {
                currentUser.login(token)
                CookieUtils.addCookie(response,Constants.ADMIN_TOKEN,RandomUtil.randomStringFixLength(5),"/",Constants.MAX_AGE,request.serverName,false)
                CookieUtils.addCookie(response,Constants.AUTH,generateToken,"/",Constants.MAX_AGE,request.serverName,true)

                info["result"] = "success"
            } catch (e: AuthenticationException) {
                info["result"] = "fail"
            }
        }

        return CommonUtil.successJson(info)
    }

    /**
     * 根据用户名和密码查询对应的用户
     */
    override fun getUser(username: String, password: String): SysUser? {
        return userService!!.findUserByUsernameAndPassword(username, password)
    }

    /**
     * 退出登录
     */
    override fun logout(request: HttpServletRequest, response: HttpServletResponse): JSONObject {
        try {
            //清除cookie中的token
            CookieUtils.delCookie(response, Cookie(Constants.ADMIN_TOKEN,null),request.serverName)
            CookieUtils.delCookie(response, Cookie(Constants.AUTH,null),request.serverName)
            CookieUtils.delCookie(response, Cookie(Constants.AUTH_PERM,null),request.serverName)
            val currentUser = SecurityUtils.getSubject()
            currentUser.logout()
        } catch (e: Exception) {
        }

        return CommonUtil.successJson()
    }
}