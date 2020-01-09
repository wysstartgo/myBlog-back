package com.ambition.business.user.domain

/**
 * <pre>
 *
 * 权限树
 * @title: PermissionTree
 * @description:
 * @author: wuys
 * @datetime: 2020-01-03 15:51
 * </pre>
 */
data class PermissionTree(
        var id: Int? = null,
        var pid: Int? = null,
        var children: MutableList<PermissionTree>? = null,
        var menuName: String? = null
)