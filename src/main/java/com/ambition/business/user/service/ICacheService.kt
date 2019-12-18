package com.ambition.business.user.service

import com.alibaba.fastjson.JSONObject

/**
 * <pre>
 *
 *
 * @title: ICacheService
 * @description:
 * @company: 润投科技
 * @author: wuys
 * @datetime: 2019-12-18 15:19
 * </pre>
 */
interface ICacheService {

    /**
     * 组织缓存
     */
    fun groupCacheList():List<JSONObject>
}