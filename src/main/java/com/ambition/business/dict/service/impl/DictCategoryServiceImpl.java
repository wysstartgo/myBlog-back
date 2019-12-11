package com.ambition.business.dict.service.impl;

import com.ambition.business.dict.domain.DictCategory;
import com.ambition.business.dict.mapper.DictCategoryMapper;
import com.ambition.business.dict.service.IDictCategoryService;
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
public class DictCategoryServiceImpl extends ServiceImpl<DictCategoryMapper, DictCategory> implements IDictCategoryService {

    @Override
    @Transactional
    public R findListByPage(int page, int pageSize,Long groupId){
     if (pageSize == 0) {
        pageSize = Constants.DEFAULT_PAGESIZE;
     }
     Page<DictCategory> dictPage = new Page<>(page, pageSize);
     IPage<DictCategory> sysDictIPage = null;
     LambdaQueryWrapper<DictCategory> lambdaQueryWrapper = Wrappers.<DictCategory>lambdaQuery();
     sysDictIPage = baseMapper.selectPage(dictPage, lambdaQueryWrapper.select(DictCategory.class, i -> true));
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
      DictCategory e = super.getById(id);
      return R.ok(e);
    }

    @Override
    public R saveDictCategory(DictCategory entity){
      boolean r = this.save(entity);
      if(r){
        return R.ok();
       }
      return R.error();
    }

    @Override
    public R updateDictCategoryById(DictCategory entity){
        boolean r = this.updateById(entity);
        if(r){
            return R.ok();
        }
        return R.error();
    }

}
