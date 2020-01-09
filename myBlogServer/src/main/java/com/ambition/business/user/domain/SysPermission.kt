package com.ambition.business.user.domain

import io.swagger.annotations.ApiModelProperty



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
open class SysPermission(
        /**
         * 自定id,主要供前端展示权限列表分类排序使用.
         */
         var id: Int? = null,

        /**
         * 归属菜单,前端判断并展示菜单使用,
         */
         var menuCode: String? = null,

        /**
         * 菜单的中文释义
         */
         var menuName: String? = null,

        /**
         * 权限的代码/通配符,对应代码中@RequiresPermissions 的value
         */
         var permissionCode: String? = null,

        /**
         * 本权限的中文释义
         */
         var permissionName: String? = null,

        /**
         * 是否本菜单必选权限, 1.必选 2非必选 通常是"列表"权限是必选
         */
         var requiredPermission: String? = null,

         @ApiModelProperty(value = "parent id")
         var pid: Int? = null,

         @ApiModelProperty(value = "是否有children,0表示没有,9表示有")
         var children: Int? = null)