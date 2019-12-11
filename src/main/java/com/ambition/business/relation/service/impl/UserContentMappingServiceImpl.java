package com.ambition.business.relation.service.impl;

import com.ambition.business.relation.domain.UserContentMapping;
import com.ambition.business.relation.mapper.UserContentMappingMapper;
import com.ambition.business.relation.service.IUserContentMappingService;
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
public class UserContentMappingServiceImpl extends ServiceImpl<UserContentMappingMapper, UserContentMapping> implements IUserContentMappingService {

    @Override
    @Transactional
    public R findListByPage(int page, int pageSize,Long groupId){
     if (pageSize == 0) {
        pageSize = Constants.DEFAULT_PAGESIZE;
     }
     Page<UserContentMapping> dictPage = new Page<>(page, pageSize);
     IPage<UserContentMapping> sysDictIPage = null;
     LambdaQueryWrapper<UserContentMapping> lambdaQueryWrapper = Wrappers.<UserContentMapping>lambdaQuery();
     sysDictIPage = baseMapper.selectPage(dictPage, lambdaQueryWrapper.select(UserContentMapping.class, i -> true));
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
      UserContentMapping e = super.getById(id);
      return R.ok(e);
    }

    @Override
    public R saveUserContentMapping(UserContentMapping entity){
      boolean r = this.save(entity);
      if(r){
        return R.ok();
       }
      return R.error();
    }

    @Override
    public R updateUserContentMappingById(UserContentMapping entity){
        boolean r = this.updateById(entity);
        if(r){
            return R.ok();
        }
        return R.error();
    }

}
