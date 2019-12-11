package com.ambition.config.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * <pre>
 *
 *
 * @title: StatelessToken
 * @description:
 * @author: wuys
 * @datetime: 2019-11-27 10:06
 * </pre>
 */
public class StatelessToken implements AuthenticationToken {
	private String userId;

	private String token;


	private String salt;

	public StatelessToken(String userId,String token,String salt) {
		this.userId = userId;
		this.salt = salt;
		this.token = token;
	}

	@Override
	public Object getPrincipal() {
		return userId;
	}

	@Override
	public Object getCredentials() {
		return token;
	}

	public String getSalt() {
		return salt;
	}


}
