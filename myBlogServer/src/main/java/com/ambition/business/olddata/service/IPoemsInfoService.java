package com.ambition.business.olddata.service;

import com.ambition.business.olddata.domain.PoemsInfo;
import com.ambition.common.util.R;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wuys
 * @since 2019-12-23
 */
public interface IPoemsInfoService extends IService<PoemsInfo> {

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
    R savePoemsInfo(PoemsInfo entity);

    R updatePoemsInfoById(PoemsInfo entity);




}
