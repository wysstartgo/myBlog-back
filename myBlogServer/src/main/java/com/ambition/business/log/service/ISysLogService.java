package com.ambition.business.log.service;

import com.ambition.common.util.R;
import java.util.List;
import com.ambition.business.log.domain.SysLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统日志 服务类
 * </p>
 *
 * @author wuys
 * @since 2019-12-11
 */
public interface ISysLogService extends IService<SysLog> {

     /**
     * 分页查询
     */
    R findListByPage(int page, int pageSize,Long groupId);

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
    R saveSysLog(SysLog entity);

    R updateSysLogById(SysLog entity);




}
