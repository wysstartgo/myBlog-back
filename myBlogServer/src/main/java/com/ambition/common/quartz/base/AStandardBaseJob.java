package com.ambition.common.quartz.base;

import org.quartz.Job;

import java.io.Serializable;

/**
 * <pre>
 *
 * @title : 标准job的父类
 * @description :
 * @company : R
 * @author : wuys
 * @time: 2018-03-19 17:19
 * </pre>
 */
public abstract class AStandardBaseJob implements Job, Serializable {

    /**
     * 初始化appid
     *
     * @Description
     * @author 唐宋
     * @return appId
     */
    public abstract String initAppId();

    /**
     * 初始化Cron表达式key
     *
     * @Description
     * @author 唐宋
     * @return cronKey
     */
    public abstract String initCronKey();

    /**
     * 初始化Cron表达式值
     *
     * @Description
     * @author 唐宋
     * @return 表达式值
     */
    public abstract String initCronValue();

    /**
     * 初始化有效标志
     *
     * @Description
     * @author 唐宋
     * @return 表达式值
     */
    public abstract int initValidSign();
}
