package com.ambition.business.content.service.impl;

import com.ambition.business.content.domain.ContentBase;
import com.ambition.business.content.mapper.ContentBaseMapper;
import com.ambition.business.content.service.IContentBaseService;
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
public class ContentBaseServiceImpl extends ServiceImpl<ContentBaseMapper, ContentBase> implements IContentBaseService {

    @Override
    @Transactional
    public R findListByPage(int page, int pageSize,Long groupId){
     if (pageSize == 0) {
        pageSize = Constants.DEFAULT_PAGESIZE;
     }
     Page<ContentBase> dictPage = new Page<>(page, pageSize);
     IPage<ContentBase> sysDictIPage = null;
     LambdaQueryWrapper<ContentBase> lambdaQueryWrapper = Wrappers.<ContentBase>lambdaQuery();
     sysDictIPage = baseMapper.selectPage(dictPage, lambdaQueryWrapper.select(ContentBase.class, i -> true));
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
      ContentBase e = super.getById(id);
      return R.ok(e);
    }

    @Override
    public R saveContentBase(ContentBase entity){
      boolean r = this.save(entity);
      if(r){
        return R.ok();
       }
      return R.error();
    }

    @Override
    public R updateContentBaseById(ContentBase entity){
        boolean r = this.updateById(entity);
        if(r){
            return R.ok();
        }
        return R.error();
    }

}
