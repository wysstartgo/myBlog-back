package com.ambition.business.olddata.service.impl;

import com.ambition.business.olddata.domain.PoemsInfo;
import com.ambition.business.olddata.mapper.PoemsInfoMapper;
import com.ambition.business.olddata.service.IPoemsInfoService;
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
 * @since 2019-12-23
 */
@Service
public class PoemsInfoServiceImpl extends ServiceImpl<PoemsInfoMapper, PoemsInfo> implements IPoemsInfoService {

    @Override
    @Transactional
    public R findListByPage(int page, int pageSize,Long groupId){
     if (pageSize == 0) {
        pageSize = Constants.DEFAULT_PAGESIZE;
     }
     Page<PoemsInfo> dictPage = new Page<>(page, pageSize);
     IPage<PoemsInfo> sysDictIPage = null;
     LambdaQueryWrapper<PoemsInfo> lambdaQueryWrapper = Wrappers.<PoemsInfo>lambdaQuery();
     sysDictIPage = baseMapper.selectPage(dictPage, lambdaQueryWrapper.select(PoemsInfo.class, i -> true));
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
      PoemsInfo e = super.getById(id);
      return R.ok(e);
    }

    @Override
    public R savePoemsInfo(PoemsInfo entity){
      boolean r = this.save(entity);
      if(r){
        return R.ok();
       }
      return R.error();
    }

    @Override
    public R updatePoemsInfoById(PoemsInfo entity){
        boolean r = this.updateById(entity);
        if(r){
            return R.ok();
        }
        return R.error();
    }

}
