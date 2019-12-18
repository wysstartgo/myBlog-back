package com.ambition.config.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.ambition.business.log.domain.SysLog;

/**
 * <pre>
 *
 *
 * @title: ContextHolder
 * @description:
 * @company:
 * @author: wuys
 * @datetime: 2019-08-06 14:03
 * </pre>
 */
public class ContextHolder {

	private static ThreadLocal<SysLog> sysLogThreadLocal = new TransmittableThreadLocal<>();

	/**
	 * 设置
	 * @param sysLog
	 */
	public static void setSysLog(SysLog sysLog){
		sysLogThreadLocal.set(sysLog);
	}

	/**
	 * 获取
	 * @return
	 */
	public static SysLog getSysLog(){
		return sysLogThreadLocal.get();
	}


}
