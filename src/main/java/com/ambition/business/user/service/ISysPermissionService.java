package com.ambition.business.user.service;

import com.ambition.business.user.domain.SysPermission;
import com.ambition.common.util.R;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 后台权限表 服务类
 * </p>
 *
 * @author wuys
 * @since 2019-12-11
 */
public interface ISysPermissionService extends IService<SysPermission> {

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
    R saveSysPermission(SysPermission entity);

    R updateSysPermissionById(SysPermission entity);




}
