package com.ambition.business.olddata.service.impl;

import com.ambition.business.olddata.domain.PoemsAuthor;
import com.ambition.business.olddata.mapper.PoemsAuthorMapper;
import com.ambition.business.olddata.service.IPoemsAuthorService;
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
 * @since 2019-12-24
 */
@Service
public class PoemsAuthorServiceImpl extends ServiceImpl<PoemsAuthorMapper, PoemsAuthor> implements IPoemsAuthorService {

    @Override
    @Transactional
    public R findListByPage(int page, int pageSize,Long groupId){
     if (pageSize == 0) {
        pageSize = Constants.DEFAULT_PAGESIZE;
     }
     Page<PoemsAuthor> dictPage = new Page<>(page, pageSize);
     IPage<PoemsAuthor> sysDictIPage = null;
     LambdaQueryWrapper<PoemsAuthor> lambdaQueryWrapper = Wrappers.<PoemsAuthor>lambdaQuery();
     sysDictIPage = baseMapper.selectPage(dictPage, lambdaQueryWrapper.select(PoemsAuthor.class, i -> true));
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
      PoemsAuthor e = super.getById(id);
      return R.ok(e);
    }

    @Override
    public R savePoemsAuthor(PoemsAuthor entity){
      boolean r = this.save(entity);
      if(r){
        return R.ok();
       }
      return R.error();
    }

    @Override
    public R updatePoemsAuthorById(PoemsAuthor entity){
        boolean r = this.updateById(entity);
        if(r){
            return R.ok();
        }
        return R.error();
    }

}
