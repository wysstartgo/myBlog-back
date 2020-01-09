package com.ambition.config.shiro;

import com.alibaba.fastjson.JSONObject;
import com.ambition.business.user.service.ISysPermissionService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
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
	private Logger LOG = LoggerFactory.getLogger(UserRealm.class);

	@Autowired
	private ISysPermissionService permissionService;

	@Override
	public boolean supports(AuthenticationToken token) {
		//仅支持StatelessToken类型的
		return token instanceof StatelessToken;
	}



	/**
	 * 这里虽然不能直接取到request的cookie中的权限信息，但是因为有缓存，所以不至于太频繁，
	 * 目前是去库中查询权限列表来做处理，也可以自己加一层后端缓存来减少查询频率
	 *
	 * 授权
	 * @param principals
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//为当前用户设置角色和权限
		//授权
		String userId = (String) principals.getPrimaryPrincipal();
		if (userId == null){
			return null;
		}
		JSONObject permission = permissionService.getUserPermission(Long.parseLong(userId));
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
		String userId = (String) statelessToken.getPrincipal();
		//密码，shiro会根据token的credentials进行加密然后与SimpleAuthenticationInfo的第二个参数进行比较
		//String loginedToken = (String) statelessToken.getCredentials();
		//通过校验
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				userId,
				statelessToken.getCredentials(),
				ByteSource.Util.bytes(statelessToken.getSalt()),
				getName()
		);

		return authenticationInfo;
	}
}
