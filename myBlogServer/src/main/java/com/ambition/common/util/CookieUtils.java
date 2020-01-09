package com.ambition.common.util;


import com.alibaba.fastjson.JSONObject;
import com.ambition.business.user.domain.SysUser;
import com.ambition.common.constants.Constants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author scf
 */
public class CookieUtils {

	private static final Logger LOG = LoggerFactory.getLogger(CookieUtils.class);

	/**
	 * 从cookie中读取数据
	 * @param request
	 * @return
	 */
	public static Optional<SysUser> readCookieUserValue(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			Optional<Cookie> cookieOptional = Arrays.stream(cookies).filter(cookie -> Constants.AUTH.equals(cookie.getName())).findFirst();
			if (cookieOptional.isPresent()) {
				Cookie cookie = cookieOptional.get();
				try {
					SysUser sysUser = JwtUtil.getUserInfoFromToken(cookie.getValue());
					return Optional.of(sysUser);
				} catch (Exception e) {
					LOG.error("cookie的值为:" + cookie.getValue() + "解密失败!", e);
				}
			}
		}
		return Optional.empty();
	}

	/**
	 * 从cookie中获取权限信息
	 * @param request
	 * @return
	 */
	public static Optional<JSONObject> readCookiePermValue(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			Optional<Cookie> cookieOptional = Arrays.stream(cookies).filter(cookie -> Constants.AUTH_PERM.equals(cookie.getName())).findFirst();
			if (cookieOptional.isPresent()) {
				Cookie cookie = cookieOptional.get();
				try {
					JSONObject jsonObject = JwtUtil.getUserPermFromToken(cookie.getValue());
					return Optional.of(jsonObject);
				} catch (Exception e) {
					LOG.error("cookie的值为:" + cookie.getValue() + "解密失败!", e);
				}
			}
		}
		return Optional.empty();
	}



	/**
	 * 从cookie中读取数据
	 * @param request
	 * @return
	 */
	public static String readToken(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			Optional<Cookie> cookieOptional = Arrays.stream(cookies).filter(cookie -> Constants.AUTH.equals(cookie.getName())).findFirst();
			if (cookieOptional.isPresent()) {
				Cookie cookie = cookieOptional.get();
				try {
					return cookie.getValue();
				} catch (Exception e) {
					LOG.error("cookie的值为:" + cookie.getValue() + "解密失败!", e);
				}
			}
		}
		return null;
	}


	/**
	 * 设置 Cookie, 过期时间为30分钟
	 *
	 * @param name  名称
	 * @param value 值
	 */
	public static Cookie setCookie(HttpServletResponse response, String name, String value, String path) {
		return addCookie(response, name, value, path, 60 * 30, "zhongwanshequ.cn",false);
	}

	/**
	 * 设置 Cookie, 过期时间自定义
	 *
	 * @param name   名称
	 * @param value  值
	 * @param maxAge 过期时间, 单位秒
	 */
	public static Cookie addCookie(HttpServletResponse response, String name, String value, String path, int maxAge, String domain,boolean isHttpOnly) {
		Cookie cookie = null;
		try {
			cookie = new Cookie(name, URLEncoder.encode(value, "UTF-8"));
			cookie.setHttpOnly(isHttpOnly);
			cookie.setMaxAge(maxAge);
			if (!RegExUtil.isIP(domain)) {
				//不是ip就切出顶级域名，为了与子域名共享cookie
				domain = domain.substring(domain.indexOf(".") + 1);
			}
			LOG.info("==================domain is:" + domain);
			//cookie.setDomain(null);
			if (null != path) {
				cookie.setPath(path);
			} else {
				cookie.setPath("/");
			}
			response.addCookie(cookie);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return cookie;
	}

	public static void delCookie(HttpServletResponse response, Cookie cookie, String domain) {
		if (cookie != null) {
			cookie = new Cookie(cookie.getName(), null);
			cookie.setPath("/");
			if (StringUtils.isNoneBlank(domain)) {
				if (!RegExUtil.isIP(domain)) {
					domain = domain.substring(domain.indexOf(".") + 1);
				}
				cookie.setDomain(domain);
			}
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
	}


	/**
	 * 设置 Cookies, 过期时间自定义
	 *
	 * @param response 响应对象
	 * @param values   值
	 * @param path     路径
	 * @param maxAge   过期时间, 单位秒
	 * @return Cookies
	 */
	public static ArrayList<Cookie> addCookies(HttpServletResponse response, Map<String, String> values, String path, int maxAge) {
		Set<Map.Entry<String, String>> entries = values.entrySet();
		ArrayList<Cookie> cookies = new ArrayList<Cookie>();
		try {
			for (Map.Entry<String, String> entry : entries) {
				Cookie cookie = new Cookie(entry.getKey(), URLEncoder.encode(entry.getValue(), "UTF-8"));
				cookie.setMaxAge(maxAge);
				if (null != path) {
					cookie.setPath(path);
				}
				response.addCookie(cookie);
				cookies.add(cookie);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return cookies;
	}



	/**
	 * 获得指定Cookie的值
	 *
	 * @param name 名称
	 * @return 值
	 */
	public static String getCookie(HttpServletRequest request, String name) {
		return getCookie(request, null, name, false);
	}

	/**
	 * 获得指定Cookie的值，并删除。
	 *
	 * @param name 名称
	 * @return 值
	 */
	public static String getCookie(HttpServletRequest request, HttpServletResponse response, String name) {
		return getCookie(request, response, name, true);
	}

	/**
	 * 获得指定Cookie的值
	 *
	 * @param request   请求对象
	 * @param response  响应对象
	 * @param name      名字
	 * @param isRemoved 是否移除
	 * @return 值
	 */
	public static String getCookie(HttpServletRequest request, HttpServletResponse response, String name, boolean isRemoved) {
		String value = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					try {
						value = URLDecoder.decode(cookie.getValue(), "UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					if (isRemoved) {
						cookie.setMaxAge(0);
						response.addCookie(cookie);
					}
					return value;
				}
			}
		}
		return value;
	}

}