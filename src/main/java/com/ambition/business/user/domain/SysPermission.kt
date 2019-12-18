package com.ambition.business.user.domain

/**
 * <pre>
 *
 *
 * @title: SysPermission
 * @description:
 * @company: 润投科技
 * @author: wuys
 * @datetime: 2019-12-18 17:14
 * </pre>
 */
data class SysPermission(
        /**
         * 自定id,主要供前端展示权限列表分类排序使用.
         */
        private var id: Int,

        /**
         * 归属菜单,前端判断并展示菜单使用,
         */
        private var menuCode: Int,

        /**
         * 菜单的中文释义
         */
        private var menuName: String,

        /**
         * 权限的代码/通配符,对应代码中@RequiresPermissions 的value
         */
        private var permissionCode: String,

        /**
         * 本权限的中文释义
         */
        private var permissionName: String,

        /**
         * 是否本菜单必选权限, 1.必选 2非必选 通常是"列表"权限是必选
         */
        private var requiredPermission: String)