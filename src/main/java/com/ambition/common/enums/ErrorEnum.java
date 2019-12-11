package com.ambition.common.enums;

/**
 * @author: hxy
 * @date: 2017/10/24 10:16
 */
public enum ErrorEnum {
	/*
	 * 错误信息
	 * */
	E_400("400", "请求处理异常，请稍后再试"),
	E_500("500", "请求方式有误,请检查 GET/POST"),
	E_501("501", "请求路径不存在"),
	E_502("502", "权限不足"),
	E_10008("10008", "角色删除失败,尚有用户属于此角色"),
	E_10009("10009", "账户已存在"),

	E_20011("20011", "登陆已过期,请重新登陆"),

	E_90003("90003", "缺少必填参数"),

	PARAMETER_ERR("90004","参数错误"),

	PATH_ERR("404", "路径不存在，请检查路径是否正确"),

	EXIST("300", "数据库中已存在该记录"),

	AUTH_DENIED("403", "没有权限，请联系管理员授权"),

	USER_NOT_LOGIN("90004","用户未登录!"),
	Store_Not_Enough("90005","库存不足!"),
	Medic_Not_Found("90006","该药品不存在!"),
	Medic_Exist("90007","该名称的药品已经存在!"),
	Store_Unit_Not_Invalid("90008","药品单位既不是包装单位也不是最小单位!"),
	Productor_Not_Exist("90009","药品供应商不存在，无法修改，请前往添加"),
	EXCEL_CANNOT_EMPTY("90010","excel不能为空"),
	Patient_User_Not_Found_InShop("90011","在该店铺没找到患者");

	private String errorCode;

	private String errorMsg;

	ErrorEnum(String errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public static void main(String[] args) {
		System.out.println(7 % 9);
	}

}