package com.ambition.config.shiro;

import com.alibaba.fastjson.JSONObject;
import com.ambition.business.user.service.ISysUserService;
import com.ambition.common.constants.Constants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

/**
 * @author: hxy
 * @description: 自定义Realm
 * @date: 2017/10/24 10:06
 */
public class UserRealm extends AuthorizingRealm {
	private Logger logger = LoggerFactory.getLogger(UserRealm.class);

	@Autowired
	private ISysUserService loginService;

	@Override
	public boolean supports(AuthenticationToken token) {
		//仅支持StatelessToken类型的
		return token instanceof StatelessToken;
	}

	/**
	 * 授权
	 * @param principals
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Session session = SecurityUtils.getSubject().getSession();
		//查询用户的权限
		JSONObject permission = (JSONObject) session.getAttribute(Constants.SESSION_USER_PERMISSION);
//		logger.info("permission的值为:" + permission);
//		logger.info("本用户权限为:" + permission.get("permissionList"));
		//为当前用户设置角色和权限
		//授权
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.addStringPermissions((Collection<String>) permission.get("permissionList"));
		return authorizationInfo;
	}

	/**
	 * 认证
	 *
	 * 验证当前登录的Subject
	 * LoginController.login()方法中执行Subject.login()时 执行此方法进行认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		StatelessToken statelessToken = (StatelessToken) authcToken;
		String username = (String) statelessToken.getPrincipal();
		//密码，shiro会根据token的credentials进行加密然后与SimpleAuthenticationInfo的第二个参数进行比较
		//String loginedToken = (String) statelessToken.getCredentials();
		//通过校验
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				username,
				statelessToken.getCredentials(),
				ByteSource.Util.bytes(statelessToken.getSalt()),
				getName()
		);

		return authenticationInfo;
	}
}
