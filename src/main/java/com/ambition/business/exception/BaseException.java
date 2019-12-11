package com.ambition.business.exception;

import com.ambition.common.enums.ErrorEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Classname BaseException
 * @Description 自定义异常
 * @Author
 * @Date 2019-03-29 13:21
 * @Version 1.0
 */
public class BaseException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    @Setter
    @Getter
    private String msg;

    @Setter
    @Getter
    private String code = "500";

    public BaseException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BaseException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public BaseException(String msg, String code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public BaseException(ErrorEnum errorEnum) {
        super(errorEnum.getErrorMsg());
        this.msg = errorEnum.getErrorMsg();
        this.code = errorEnum.getErrorCode();
    }

    public BaseException(String msg, String code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

}
