package com.ambition.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 *
 * @title : 当前用户
 * @description : 在Controller的方法参数中使用此注解，该方法在映射时会注入当前登录的User对象
 * @company :
 * @author : wuys
 * @time: 2018-06-06 14:49
 * </pre>
 */

@Target(ElementType.PARAMETER) // 可用在方法的参数上
@Retention(RetentionPolicy.RUNTIME) // 运行时有效
public @interface CurrentUser {

}
