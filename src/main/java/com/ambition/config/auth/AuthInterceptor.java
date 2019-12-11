package com.ambition.config.auth;

import com.alibaba.fastjson.JSONObject;
import com.ambition.business.user.domain.SysUser;
import com.ambition.common.annotations.LoginedUser;
import com.ambition.common.constants.Constants;
import com.ambition.common.enums.ErrorEnum;
import com.ambition.common.util.R;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <pre>
 *
 *
 * @title: AuthInterceptor
 * @description:
 * @company:
 * @author: wuys
 * @datetime: 2019-08-05 16:52
 * </pre>
 */
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

	private static final Logger LOG = LoggerFactory.getLogger(AuthInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		PrintWriter out = null;
		if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf-8");
			response.setDateHeader("expries", -1);
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			LoginedUser authPassport = ((HandlerMethod) handler).getMethodAnnotation(LoginedUser.class);
			// 没有声明需要权限,或者声明不验证权限
//			String servletPath = request.getServletPath();
//			String[] split = servletPath.split("/");
			if (authPassport == null || !authPassport.needLogin()) {
				return true;
			}
			SysUser sysUser = (SysUser) SecurityUtils.getSubject().getSession().getAttribute(Constants.SESSION_USER_INFO);
			if (null != sysUser) {
				request.setAttribute(Constants.CURRENT_USER,sysUser);
				return true;
			} else {
				try {
					out = response.getWriter();
					out.append(JSONObject.toJSONString(R.error(ErrorEnum.USER_NOT_LOGIN.getErrorCode(),ErrorEnum.USER_NOT_LOGIN.getErrorMsg())));
					return false;
				} catch (IOException e) {
					LOG.error("IO异常!", e);
				} finally {
					if (out != null) {
						out.close();
					}
				}
			}
		}
		return true;
	}
}
