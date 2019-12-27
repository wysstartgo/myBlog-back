package com.ambition.common.constants;

/**
 * <pre>
 *
 *
 * @title: Constants
 * @description:
 * @company:
 * @author: wuys
 * @datetime: 2019-07-26 16:06
 * </pre>
 */
public interface Constants {

	String APP_ID = "myBlog";

	/**
	 * 无效任务标识
	 */
	int VALID_SIGN_N = 0;

	long SYS_ID = 1;


	int DEFAULT_PAGESIZE = 20;

	String SUCC_CODE = "200";

	String RESET_MAIL = "MAIL";

	String PRE_IMAGE_SESSION_KEY = "PRE_IMAGE_SESSION_KEY";

	String SUCCESS_CODE = "100";
	String SUCCESS_MSG = "请求成功";

	/**
	 * session中存放用户信息的key值
	 */
	String SESSION_USER_INFO = "userInfo";


	String CURRENT_USER = "current_user";

	String GROUP_ALL_KEY = "group_all";


	String SHIRO_KEY = "blog_key";

	String SHIRO_SALT = "zhongwanshequ";

	String AUTH = "auth_token";

	String AUTH_PERM = "perm_auth";

	String ADMIN_TOKEN = "admin-token";

	int MAX_AGE = 60 * 60;


}
