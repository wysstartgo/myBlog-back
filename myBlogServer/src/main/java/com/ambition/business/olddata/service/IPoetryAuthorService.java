package com.ambition.business.olddata.service;

import com.ambition.common.util.R;
import java.util.List;
import com.ambition.business.olddata.domain.PoetryAuthor;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wuys
 * @since 2019-12-24
 */
public interface IPoetryAuthorService extends IService<PoetryAuthor> {

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
    R savePoetryAuthor(PoetryAuthor entity);

    R updatePoetryAuthorById(PoetryAuthor entity);




}
