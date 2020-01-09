package com.ambition.common.util;

import com.alibaba.fastjson.JSONObject;
import com.ambition.business.user.domain.SysUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname JwtUtil
 * @Description JWT工具类
 * @Date 2019-05-07 09:23
 */
public class JwtUtil {

	private static final Logger LOG = LoggerFactory.getLogger(JwtUtil.class);

	private static final String INFO = "info";
	/**
	 * 创建时间
	 */
	private static final String CREATED = "created";
	/**
	 * 权限列表
	 */
	private static final String AUTHORITIES = "authorities";
	/**
	 * 密钥
	 */
	private static final String SECRET = "zhourunfa@666club";

	/**
	 * 公司
	 */
	private static final String COMPANY = "zhongwanshequ";

	/**
	 * 盐值
	 */
	private static final String SALT = "86yingxiongbengshe";

	/**
	 * 有效期1小时
	 */
	private static final long EXPIRE_TIME = 60 * 60 * 1000;


	/**
	 * 生成令牌
	 *
	 * @return 令牌
	 */
	public static String generateToken(SysUser userDetail) {
		Map<String, Object> claims = new HashMap<>(3);
		userDetail.setPassword(null);
		userDetail.setIdCard(null);
		claims.put(INFO, JSONObject.toJSONString(userDetail));
		claims.put(CREATED, new Date());
		claims.put("company", COMPANY);
		return encode(SECRET, claims, SALT);
	}

	/**
	 * 生成权限token
	 * @param jsonObject
	 * @return
	 */
	public static String generatePermToken(JSONObject jsonObject){
		if (jsonObject == null){
			return "";
		}
		Map<String, Object> claims = new HashMap<>(3);
		claims.put(INFO, jsonObject.toJSONString());
		claims.put(CREATED, new Date());
		claims.put("company", COMPANY);
		return encode(SECRET, claims, SALT);
	}

	/**
	 * 获取权限信息
	 * @param token
	 * @return
	 */
	public static JSONObject getUserPermFromToken(String token) {
		Claims claims = decode(SECRET, token, SALT);
		Object obj = claims.get(INFO);
		if (obj == null) {
			return null;
		}
		if (obj instanceof String) {
			String infoStr = (String) obj;
			return JSONObject.parseObject(infoStr);
		}
		return null;
	}

	/**
	 * 从token中获取用户信息
	 * @param token
	 * @return
	 */
	public static SysUser getUserInfoFromToken(String token) {
		Claims claims = decode(SECRET, token, SALT);
		Object obj = claims.get(INFO);
		if (obj == null) {
			return null;
		}
		if (obj instanceof String) {
			String infoStr = (String) obj;
			SysUser sysUser = JSONObject.parseObject(infoStr, SysUser.class);
			return sysUser;
		}
		return null;
	}

	/**
	 * 从令牌中获取数据声明
	 *
	 * @param token 令牌
	 * @return 数据声明
	 */
	private static Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	/**
	 * 判断令牌是否过期
	 *
	 * @param token 令牌
	 * @return 是否过期
	 */
	public static Boolean isTokenExpired(String token) {
		try {
			Claims claims = decode(SECRET, token, SALT);
			Date expiration = claims.getExpiration();
			return expiration.before(new Date());
		} catch (Exception e) {
			LOG.error("", e);
			return false;
		}
	}

	/**
	 * 需要key.map,颜值三个组成部分
	 * 签名部分
	 * 根据用户信息+盐值+密钥生成的签名。
	 *
	 * @param key
	 * @param param
	 * @param salt
	 * @return
	 */
	public static String encode(String key, Map<String, Object> param, String salt) {
		if (salt != null && salt.length() > 0) {
			key += salt;
		}
		//生成签名部分
		JwtBuilder jwtBuilder = Jwts.builder().signWith(SignatureAlgorithm.HS256, key);
		Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
		jwtBuilder = jwtBuilder.setClaims(param).setExpiration(date);
		//获取生成的token
		String token = jwtBuilder.compact();
		return token;
	}

	/**
	 * 解密的过程
	 *
	 * @param key
	 * @param token
	 * @param salt
	 * @return
	 */
	public static Claims decode(String key, String token, String salt) {
		Claims claims = null;
		if (salt != null && salt.length() > 0) {
			key += salt;
		}

		claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
		return claims;
	}

}
