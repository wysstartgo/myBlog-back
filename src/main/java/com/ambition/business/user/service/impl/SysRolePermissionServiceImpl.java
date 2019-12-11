package com.ambition.business.user.service.impl;

import com.ambition.business.user.domain.SysRolePermission;
import com.ambition.business.user.mapper.SysRolePermissionMapper;
import com.ambition.business.user.service.ISysRolePermissionService;
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
 * 角色-权限关联表 服务实现类
 * </p>
 *
 * @author wuys
 * @since 2019-12-11
 */
@Service
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements ISysRolePermissionService {

    @Override
    @Transactional
    public R findListByPage(int page, int pageSize,Long groupId){
     if (pageSize == 0) {
        pageSize = Constants.DEFAULT_PAGESIZE;
     }
     Page<SysRolePermission> dictPage = new Page<>(page, pageSize);
     IPage<SysRolePermission> sysDictIPage = null;
     LambdaQueryWrapper<SysRolePermission> lambdaQueryWrapper = Wrappers.<SysRolePermission>lambdaQuery();
     sysDictIPage = baseMapper.selectPage(dictPage, lambdaQueryWrapper.select(SysRolePermission.class, i -> true));
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
      SysRolePermission e = super.getById(id);
      return R.ok(e);
    }

    @Override
    public R saveSysRolePermission(SysRolePermission entity){
      boolean r = this.save(entity);
      if(r){
        return R.ok();
       }
      return R.error();
    }

    @Override
    public R updateSysRolePermissionById(SysRolePermission entity){
        boolean r = this.updateById(entity);
        if(r){
            return R.ok();
        }
        return R.error();
    }

}
