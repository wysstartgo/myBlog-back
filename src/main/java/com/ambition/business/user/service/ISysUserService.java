package com.ambition.business.user.service;

import com.ambition.common.util.R;
import java.util.List;
import com.ambition.business.user.domain.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 运营后台用户表 服务类
 * </p>
 *
 * @author wuys
 * @since 2019-12-11
 */
public interface ISysUserService extends IService<SysUser> {

     /**
     * 分页查询
     */
    R findListByPage(int page, int pageSize, Long groupId);

    /**
    * 删除多条
    */
    R deleteByIds(List<Long> ids);

    /**
      *通过id查询
      */
    R getById(Long id);

    /**
        * 保存
        */
    R saveSysUser(SysUser entity);

    R updateSysUserById(SysUser entity);




}
