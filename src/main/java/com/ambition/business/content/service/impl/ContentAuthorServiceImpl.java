package com.ambition.business.content.service.impl;

import com.ambition.business.content.domain.ContentAuthor;
import com.ambition.business.content.mapper.ContentAuthorMapper;
import com.ambition.business.content.service.IContentAuthorService;
import com.ambition.common.constants.Constants;
import com.ambition.common.util.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wuys
 * @since 2019-12-24
 */
@Service
public class ContentAuthorServiceImpl extends ServiceImpl<ContentAuthorMapper, ContentAuthor> implements IContentAuthorService {

    @Override
    @Transactional
    public R findListByPage(int page, int pageSize,Long groupId){
     if (pageSize == 0) {
        pageSize = Constants.DEFAULT_PAGESIZE;
     }
     Page<ContentAuthor> dictPage = new Page<>(page, pageSize);
     IPage<ContentAuthor> sysDictIPage = null;
     LambdaQueryWrapper<ContentAuthor> lambdaQueryWrapper = Wrappers.<ContentAuthor>lambdaQuery();
     sysDictIPage = baseMapper.selectPage(dictPage, lambdaQueryWrapper.select(ContentAuthor.class, i -> true));
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
      ContentAuthor e = super.getById(id);
      return R.ok(e);
    }

    @Override
    public R saveContentAuthor(ContentAuthor entity){
      boolean r = this.save(entity);
      if(r){
        return R.ok();
       }
      return R.error();
    }

    @Override
    public R updateContentAuthorById(ContentAuthor entity){
        boolean r = this.updateById(entity);
        if(r){
            return R.ok();
        }
        return R.error();
    }

}
