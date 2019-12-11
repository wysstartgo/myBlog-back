package com.ambition.business.user.service;

import com.ambition.common.util.R;
import java.util.List;
import com.ambition.business.user.domain.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 后台角色表 服务类
 * </p>
 *
 * @author wuys
 * @since 2019-12-11
 */
public interface ISysRoleService extends IService<SysRole> {

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
    R saveSysRole(SysRole entity);

    R updateSysRoleById(SysRole entity);




}
