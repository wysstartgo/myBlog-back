package com.ambition.config.shiro;

import com.ambition.business.user.domain.SysUser;
import com.ambition.common.util.JwtUtil;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * <pre>
 *
 *
 * @title: JWTCredentialsMatcher
 * @description:
 * @author: wuys
 * @datetime: 2019-11-29 15:00
 * </pre>
 */
public class JWTCredentialsMatcher implements CredentialsMatcher {

	/**
	 * @param token
	 * @param info
	 * @return
	 */
	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		if (info instanceof SimpleAuthenticationInfo) {
			SimpleAuthenticationInfo simpleAuthenticationInfo = (SimpleAuthenticationInfo) info;
			PrincipalCollection principals = info.getPrincipals();
			if (principals.isEmpty()) {
				return false;
			}
			if (token instanceof StatelessToken) {
				//用户信息
				String primaryPrincipal = (String) principals.getPrimaryPrincipal();
				String tokenPrincipal = (String) token.getPrincipal();
				String credentials = (String) info.getCredentials();
				String credentials1 = (String) token.getCredentials();
				String toHex = simpleAuthenticationInfo.getCredentialsSalt().toHex();
				StatelessToken statelessToken = (StatelessToken) token;
				String toHex1 = ByteSource.Util.bytes(statelessToken.getSalt()).toHex();
				if (primaryPrincipal.equals(tokenPrincipal) && credentials.equals(credentials1) && toHex.equals(toHex1)) {
					if (JwtUtil.isTokenExpired(credentials)) {
						return false;
					}
					SysUser sysUser = JwtUtil.getUserInfoFromToken(credentials);
					String username = sysUser.getUsername();
					if (primaryPrincipal.equals(username)) {
						return true;
					}
					return false;
				}
				return false;
			} else {
				return false;
			}
		}

		return false;
	}
}
