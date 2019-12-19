package com.ambition.business.user.service.impl

import com.alibaba.fastjson.JSONObject
import com.ambition.business.user.service.ICacheService
import com.ambition.business.user.service.ISysGroupService
import com.ambition.common.constants.Constants
import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import com.google.common.cache.LoadingCache
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit
import javax.annotation.Resource


/**
 * <pre>
 *
 *
 * @title: CacheServiceImpl
 * @description:
 * @company: 润投科技
 * @author: wuys
 * @datetime: 2019-12-18 15:20
 * </pre>
 */
@Service
class CacheServiceImpl:ICacheService {

    private val LOG = LoggerFactory.getLogger(CacheServiceImpl::class.java)

    @Resource
    private val sysGroupService: ISysGroupService? = null

    /**
     * 店铺缓存
     */
    private var SHOP_CACHE: LoadingCache<String, List<JSONObject>>? = null

    override fun groupCacheList(): List<JSONObject> {
        if (SHOP_CACHE == null) {
            synchronized(this) {
                if (SHOP_CACHE == null) {
                    SHOP_CACHE = CacheBuilder.newBuilder().maximumSize(2000)
                            //并发级别，即锁粒度
                            .concurrencyLevel(8).expireAfterAccess(30, TimeUnit.SECONDS).build(object : CacheLoader<String, List<JSONObject>>() {
                                @Throws(Exception::class)
                                override fun load(key: String): List<JSONObject>? {
                                    var groupList = sysGroupService!!.getAllGroups()
                                    if (groupList == null) {
                                        groupList = Collections.EMPTY_LIST as List<JSONObject>
                                    }
                                    return groupList
                                }
                            })
                }
            }
        }
        try {
            return SHOP_CACHE!!.get(Constants.GROUP_ALL_KEY)
        } catch (e: ExecutionException) {
            LOG.error("缓存加载异常!", e)
        }

        return Collections.EMPTY_LIST as List<JSONObject>
    }
}