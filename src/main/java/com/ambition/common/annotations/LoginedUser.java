package com.ambition.common.annotations;

import java.lang.annotation.*;

/**
 * <pre>
 *
 *
 * @title: LoginedUser
 * @description:
 * @company:
 * @author: wuys
 * @datetime: 2019-08-05 16:49
 * </pre>
 */
@Documented
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginedUser {

	/**
	 * true时，代表接口可登录可不登录
	 * @return
	 */
	boolean needLogin() default true;
}
