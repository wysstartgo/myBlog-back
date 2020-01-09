package com.ambition.config.shiro;

import com.alibaba.fastjson.JSONObject;
import com.ambition.business.user.domain.SysUser;
import com.ambition.common.constants.Constants;
import com.ambition.common.enums.ErrorEnum;
import com.ambition.common.util.CookieUtils;
import com.ambition.common.util.JwtUtil;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author: hxy
 * @description: 对没有登录的请求进行拦截, 全部返回json信息. 覆盖掉shiro原本的跳转login.jsp的拦截方式
 * @date: 2017/10/24 10:11
 */
public class AjaxPermissionsAuthorizationFilter extends FormAuthenticationFilter {

	private static final Logger LOG = LoggerFactory.getLogger(AjaxPermissionsAuthorizationFilter.class);

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		if(this.isLoginRequest(request, response)) {
			return true;
		}
		boolean allowed = false;
		try {
			allowed = executeLogin(request, response);
		} catch(IllegalStateException e){ //not found any token
			LOG.error("Not found any token");
		}catch (Exception e) {
			LOG.error("Error occurs when login", e);
		}
		return allowed || super.isPermissive(mappedValue);
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", ErrorEnum.E_20011.getErrorCode());
		jsonObject.put("msg", ErrorEnum.E_20011.getErrorMsg());
		PrintWriter out = null;
		HttpServletResponse res = (HttpServletResponse) response;
		try {
			res.setCharacterEncoding("UTF-8");
			res.setContentType("application/json");
			out = response.getWriter();
			out.println(jsonObject);
		} catch (Exception e) {
		} finally {
			if (null != out) {
				out.flush();
				out.close();
			}
		}
		return false;
	}

	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
//		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//		Object attribute = httpServletRequest.getAttribute(Constants.CURRENT_USER);
		String token = CookieUtils.readToken((HttpServletRequest) request);
		if (token == null){
			return null;
		}
		SysUser sysUser = JwtUtil.getUserInfoFromToken(token);
		request.setAttribute(Constants.CURRENT_USER,sysUser);
		StatelessToken statelessToken = new StatelessToken(String.valueOf(sysUser.getId()),token, Constants.SHIRO_SALT);
		return statelessToken;
	}

	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
		return true;
	}


	@Bean
	public FilterRegistrationBean registration(AjaxPermissionsAuthorizationFilter filter) {
		FilterRegistrationBean registration = new FilterRegistrationBean(filter);
		registration.setEnabled(false);
		return registration;
	}
}
