package com.ambition.business.user.domain

import com.alibaba.fastjson.JSONObject

/**
 * <pre>
 *
 *
 * @title: One2Many
 * @description:
 * @company: 润投科技
 * @author: wuys
 * @datetime: 2019-12-18 16:36
 * </pre>
 */
data class One2Many(var roleList:Set<String>?,var menuList:Set<String>?,var permissionList:Set<String>?,
               var permissionIds: Set<Int>?,var picList: List<JSONObject>?,var menus: List<JSONObject>?,
               var users: List<JSONObject>?,var permissions: List<JSONObject>?) : JSONObject() {
}