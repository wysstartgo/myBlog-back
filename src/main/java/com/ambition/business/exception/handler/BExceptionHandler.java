package com.ambition.business.exception.handler;

import com.alibaba.fastjson.JSONObject;
import com.ambition.business.exception.BaseException;
import com.ambition.business.exception.CommonJsonException;
import com.ambition.business.user.domain.SysUser;
import com.ambition.common.constants.Constants;
import com.ambition.common.enums.ErrorEnum;
import com.ambition.common.util.CommonUtil;
import com.ambition.common.util.R;
import com.ambition.common.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.security.auth.login.AccountExpiredException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.nio.file.AccessDeniedException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 * @Classname BExceptionHandler
 * @Description 自定义异常处理
 * @Author
 * @Date 2019-03-29 13:23
 * @Version 1.0
 */
@Slf4j
@RestControllerAdvice
public class BExceptionHandler {


    /**
     * 处理自定义异常
     */
    @ExceptionHandler(BaseException.class)
    public R handleRRException(BaseException e, HttpServletRequest request) {
        logErrorMsg(e,request);
        return R.error(e.getCode(), e.getMsg());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public R handlerNoFoundException(Exception e, HttpServletRequest request) {
        logErrorMsg(e,request);
        return R.error(ErrorEnum.PATH_ERR.getErrorMsg(),ErrorEnum.PATH_ERR.getErrorMsg());
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public R handleDuplicateKeyException(DuplicateKeyException e, HttpServletRequest request) {
        logErrorMsg(e,request);
        return R.error(ErrorEnum.EXIST.getErrorCode(),ErrorEnum.EXIST.getErrorMsg());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public R handleAuthorizationException(AccessDeniedException e, HttpServletRequest request) {
        logErrorMsg(e,request);
        return R.error(ErrorEnum.AUTH_DENIED.getErrorCode(),ErrorEnum.AUTH_DENIED.getErrorMsg());
    }

    private void logErrorMsg(Exception e,HttpServletRequest request) {
        log.error("",e);
        log.error(new StringBuilder("error msg is:").append(e.getMessage()).append("    ").append(getErrorMsg(request)).toString());
    }

    @ExceptionHandler(AccountExpiredException.class)
    public R handleAccountExpiredException(AccountExpiredException e, HttpServletRequest request) {
        logErrorMsg(e,request);
        return R.error(e.getMessage());
    }

    /**
     * 数据校验处理
     * @param e
     * @return
     */
    @ExceptionHandler({BindException.class, ConstraintViolationException.class})
    public String validatorExceptionHandler(Exception e) {
        String msg = e instanceof BindException ? msgConvertor(((BindException) e).getBindingResult())
                : msgConvertor(((ConstraintViolationException) e).getConstraintViolations());

        return msg;
    }

    /**
     * 获取错误请求
     *
     * @param request
     * @return
     * @Description
     * @author 赵俊凯
     */
    private String getErrorMsg(HttpServletRequest request) {
        String errorIp = RequestUtil.getClientIP(request);
        String errorPath = RequestUtil.getRequestPath(request);
        Object attribute = request.getAttribute(Constants.CURRENT_USER);
        StringBuilder msgBuilder = new StringBuilder("error request ip is:").append(errorIp)
                .append(",error request path is").append(errorPath);
        if (attribute != null) {
            SysUser userBaseTo = (SysUser) attribute;
            msgBuilder.append(",user id is:").append(userBaseTo.getId()).append(",user name is:")
                    .append(userBaseTo.getUsername());
        }
        return msgBuilder.toString();
    }

	/**
	 * 参数不合法异常
	 *
	 * @param ex
	 * @return
	 * @Description
	 * @author 唐宋
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public R handleException(MethodArgumentNotValidException ex) {
		BindingResult a = ex.getBindingResult();
		List<ObjectError> list = a.getAllErrors();
		String errorMsg = ErrorEnum.PARAMETER_ERR.getErrorMsg();
		if (CollectionUtils.isNotEmpty(list)) {
			errorMsg = list.get(0).getDefaultMessage();
		}
		return R.error(ErrorEnum.PARAMETER_ERR.getErrorCode(),errorMsg);
	}

    /**
     * 校验消息转换拼接
     *
     * @param bindingResult
     * @return
     */
    public static String msgConvertor(BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        StringBuilder sb = new StringBuilder();
        fieldErrors.forEach(fieldError -> sb.append(fieldError.getDefaultMessage()).append(","));

        return sb.deleteCharAt(sb.length() - 1).toString().toLowerCase();
    }

    private String msgConvertor(Set<ConstraintViolation<?>> constraintViolations) {
        StringBuilder sb = new StringBuilder();
        constraintViolations.forEach(violation -> sb.append(violation.getMessage()).append(","));

        return sb.deleteCharAt(sb.length() - 1).toString().toLowerCase();
    }


    /**
     * 本系统自定义错误的拦截器
     * 拦截到此错误之后,就返回这个类里面的json给前端
     * 常见使用场景是参数校验失败,抛出此错,返回错误信息给前端
     */
    @ExceptionHandler(CommonJsonException.class)
    public JSONObject commonJsonExceptionHandler(CommonJsonException commonJsonException) {
        return commonJsonException.getResultJson();
    }

    /**
     * 权限不足报错拦截
     */
    @ExceptionHandler(UnauthorizedException.class)
    public JSONObject unauthorizedExceptionHandler() {
        return CommonUtil.errorJson(ErrorEnum.E_502);
    }

    /**
     * 未登录报错拦截
     * 在请求需要权限的接口,而连登录都还没登录的时候,会报此错
     */
    @ExceptionHandler(UnauthenticatedException.class)
    public JSONObject unauthenticatedException() {
        return CommonUtil.errorJson(ErrorEnum.E_20011);
    }

    @ExceptionHandler(Exception.class)
    public R handleException(Exception e, HttpServletRequest request) {
        String errorPosition = "";
        //如果错误堆栈信息存在
        if (e.getStackTrace().length > 0) {
            StackTraceElement element = e.getStackTrace()[0];
            String fileName = element.getFileName() == null ? "未找到错误文件" : element.getFileName();
            int lineNumber = element.getLineNumber();
            errorPosition = fileName + ":" + lineNumber;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", ErrorEnum.E_400.getErrorCode());
        jsonObject.put("msg", ErrorEnum.E_400.getErrorMsg());
        JSONObject errorObject = new JSONObject();
        errorObject.put("errorLocation", e.toString() + "    错误位置:" + errorPosition);
        jsonObject.put("info", errorObject);
        logErrorMsg(e,request);
        return R.error();
    }

    @ExceptionHandler(SQLException.class)
    public R handleSQLException(SQLException e, HttpServletRequest request) {
        logErrorMsg(e,request);
        return R.error();
    }

}
