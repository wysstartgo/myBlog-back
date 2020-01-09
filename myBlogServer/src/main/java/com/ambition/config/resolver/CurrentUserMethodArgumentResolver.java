package com.ambition.config.resolver;

import com.ambition.business.user.domain.SysUser;
import com.ambition.common.annotations.CurrentUser;
import com.ambition.common.constants.Constants;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * <pre>
 *
 * @title : 增加方法注入，将含有 @CurrentUser 注解的方法参数注入当前登录用户
 * @description : 在authInterceptor中校验后传入的
 * @company :
 * @author : wuys
 * @time: 2018-06-06 14:51
 * </pre>
 */
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(SysUser.class)
                && parameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
								  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        SysUser user = null;
        if (parameter.getParameterType().isAssignableFrom(SysUser.class)
                && parameter.hasParameterAnnotation(CurrentUser.class)) {
            user = (SysUser) webRequest.getAttribute(Constants.CURRENT_USER, RequestAttributes.SCOPE_REQUEST);
        }
        return user;
    }

}
