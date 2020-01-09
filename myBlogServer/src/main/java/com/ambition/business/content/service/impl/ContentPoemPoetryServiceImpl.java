package com.ambition.business.content.service.impl;

import com.ambition.business.content.domain.ContentPoemPoetry;
import com.ambition.business.content.mapper.ContentPoemPoetryMapper;
import com.ambition.business.content.service.IContentPoemPoetryService;
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
 * 服务实现类
 * </p>
 *
 * @author wuys
 * @since 2019-12-11
 */
@Service
public class ContentPoemPoetryServiceImpl extends ServiceImpl<ContentPoemPoetryMapper, ContentPoemPoetry> implements IContentPoemPoetryService {

	@Override
	@Transactional
	public R findListByPage(int page, int pageSize, Long groupId) {
		if (pageSize == 0) {
			pageSize = Constants.DEFAULT_PAGESIZE;
		}
		Page<ContentPoemPoetry> dictPage = new Page<>(page, pageSize);
		IPage<ContentPoemPoetry> sysDictIPage = null;
		LambdaQueryWrapper<ContentPoemPoetry> lambdaQueryWrapper = Wrappers.<ContentPoemPoetry>lambdaQuery();
		sysDictIPage = baseMapper.selectPage(dictPage, lambdaQueryWrapper.select(ContentPoemPoetry.class, i -> true));
		return R.ok(sysDictIPage);
	}

	@Override
	public R deleteByIds(List<Long> ids) {
		int result = this.baseMapper.deleteBatchIds(ids);
		if (result > 0) {
			return R.ok();
		}
		return R.error();
	}

	@Override
	public R getById(Long id) {
		ContentPoemPoetry e = super.getById(id);
		return R.ok(e);
	}

	@Override
	@Transactional
	public R saveContentPoemPoetry(ContentPoemPoetry entity) {
		boolean r = this.save(entity);
		if (r) {
			return R.ok();
		}
		return R.error();
	}

	@Override
	public R updateContentPoemPoetryById(ContentPoemPoetry entity) {
		boolean r = this.updateById(entity);
		if (r) {
			return R.ok();
		}
		return R.error();
	}

}
