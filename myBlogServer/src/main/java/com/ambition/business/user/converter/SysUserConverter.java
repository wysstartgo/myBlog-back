package com.ambition.business.user.converter;

import com.ambition.business.user.domain.SysUser;
import com.ambition.business.user.vo.SysUserVo;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

/**
 * <pre>
 *
 *
 * @title: SysUserConverter
 * @description:
 * @company:
 * @author: wuys
 * @datetime: 2019-08-06 16:04
 * </pre>
 */
@Mapper
public interface SysUserConverter {

	SysUserConverter INSTANCE = Mappers.getMapper(SysUserConverter.class);

	/**
	 * 从vo转成dto 这里是SysUser
	 * @param sysUserVo
	 * @return
	 */
	@BeanMapping(resultType = SysUser.class,nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
	SysUser convertFromVo(SysUserVo sysUserVo);

	/**
	 * 从dto转成vo
	 * @param sysUser
	 * @return
	 */
	@BeanMapping(resultType = SysUserVo.class,nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
	SysUserVo convertToVo(SysUser sysUser);
}
