package com.ambition.business.olddata.service;

import com.ambition.common.util.R;
import java.util.List;
import com.ambition.business.olddata.domain.PoemsAuthor;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wuys
 * @since 2019-12-24
 */
public interface IPoemsAuthorService extends IService<PoemsAuthor> {

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
    R savePoemsAuthor(PoemsAuthor entity);

    R updatePoemsAuthorById(PoemsAuthor entity);




}
