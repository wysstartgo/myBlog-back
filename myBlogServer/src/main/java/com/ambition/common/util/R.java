package com.ambition.common.util;

import com.ambition.common.enums.ErrorEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.io.Serializable;


/**
 * @Classname R
 * @Description 响应信息主体
 * @Author
 * @Date 2019-03-27 21:54
 * @Version 1.0
 */
@Setter
@Getter
@ToString
@Accessors(chain = true)
public class R implements Serializable {

    private static final long serialVersionUID = 1L;


    private String code = "200";
    private String msg;
    private Object data;

    public static R ok() {
        R r = new R();
        r.setMsg("操作成功");
        return r;
    }

    public static R ok(Object data) {
        R r = new R();
        r.setMsg("操作成功");
        r.setData(data);
        return r;
    }

    public static R error() {
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value() + "", "未知异常，请联系管理员");
    }

    public static R error(String msg) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value() + "", msg);
    }

    public static R error(ErrorEnum errorEnum){
        return error(errorEnum.getErrorCode(),errorEnum.getErrorMsg());
    }

    public static R error(String code, String msg) {
        R r = new R();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }
}
