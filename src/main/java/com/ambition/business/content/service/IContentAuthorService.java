package com.ambition.business.content.service;

import com.ambition.business.content.domain.ContentAuthor;
import com.ambition.common.util.R;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wuys
 * @since 2019-12-24
 */
public interface IContentAuthorService extends IService<ContentAuthor> {

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
    R saveContentAuthor(ContentAuthor entity);

    R updateContentAuthorById(ContentAuthor entity);




}
