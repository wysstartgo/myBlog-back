package com.ambition.business.content.service.impl;

import com.ambition.business.content.domain.ContentArticle;
import com.ambition.business.content.mapper.ContentArticleMapper;
import com.ambition.business.content.service.IContentArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ambition.common.constants.Constants;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import com.ambition.common.util.R;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wuys
 * @since 2019-12-11
 */
@Service
public class ContentArticleServiceImpl extends ServiceImpl<ContentArticleMapper, ContentArticle> implements IContentArticleService {

    @Override
    @Transactional
    public R findListByPage(int page, int pageSize,Long groupId){
     if (pageSize == 0) {
        pageSize = Constants.DEFAULT_PAGESIZE;
     }
     Page<ContentArticle> dictPage = new Page<>(page, pageSize);
     IPage<ContentArticle> sysDictIPage = null;
     LambdaQueryWrapper<ContentArticle> lambdaQueryWrapper = Wrappers.<ContentArticle>lambdaQuery();
     sysDictIPage = baseMapper.selectPage(dictPage, lambdaQueryWrapper.select(ContentArticle.class, i -> true));
     return R.ok(sysDictIPage);
    }

    @Override
    public R deleteByIds(List<Long> ids){
     int result = this.baseMapper.deleteBatchIds(ids);
     if(result > 0){
        return R.ok();
     }
     return R.error();
    }

    @Override
    public R getById(Long id){
      ContentArticle e = super.getById(id);
      return R.ok(e);
    }

    @Override
    public R saveContentArticle(ContentArticle entity){
      boolean r = this.save(entity);
      if(r){
        return R.ok();
       }
      return R.error();
    }

    @Override
    public R updateContentArticleById(ContentArticle entity){
        boolean r = this.updateById(entity);
        if(r){
            return R.ok();
        }
        return R.error();
    }

}
