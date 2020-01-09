package com.ambition.business.content.service;

import com.ambition.common.util.R;
import java.util.List;
import com.ambition.business.content.domain.ContentBase;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wuys
 * @since 2019-12-11
 */
public interface IContentBaseService extends IService<ContentBase> {

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
    R saveContentBase(ContentBase entity);

    R updateContentBaseById(ContentBase entity);




}
