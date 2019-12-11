package com.ambition.business.user.mapper;

import com.alibaba.fastjson.JSONObject;
import com.ambition.business.user.domain.SysPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Set;

/**
 * <p>
 * 后台权限表 Mapper 接口
 * </p>
 *
 * @author wuys
 * @since 2019-12-11
 */
public interface SysPermissionMapper extends BaseMapper<SysPermission> {
	/**
	 * 查询用户的角色 菜单 权限
	 */
	JSONObject getUserPermission(String username);

	/**
	 * 查询所有的菜单
	 */
	Set<String> getAllMenu();

	/**
	 * 查询所有的权限
	 */
	Set<String> getAllPermission();

}
