package com.ambition.business.user.vo

import com.ambition.business.user.domain.SysUser

/**
 * <pre>
 *
 *
 * @title: SysUserVo
 * @description:
 * @company: 润投科技
 * @author: wuys
 * @datetime: 2019-12-19 10:35
 * </pre>
 */
open class SysUserVo : SysUser() {
    var userId: Long? = null
}