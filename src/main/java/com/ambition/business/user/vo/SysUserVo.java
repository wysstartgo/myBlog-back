package com.ambition.business.user.vo;

import com.ambition.business.user.domain.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <pre>
 *
 *
 * @title: SysUserVo
 * @description:
 * @company:
 * @author: wuys
 * @datetime: 2019-08-06 16:03
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysUserVo extends SysUser {

	private Long userId;
}
