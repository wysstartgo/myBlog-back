package com.ambition.business.user.service.impl

import com.alibaba.fastjson.JSONObject
import com.ambition.business.user.domain.SysGroup
import com.ambition.business.user.mapper.SysGroupMapper
import com.ambition.business.user.service.ISysGroupService
import com.ambition.common.constants.Constants
import com.ambition.common.enums.YesOrNoEnum
import com.ambition.common.util.R
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * <pre>
 *
 *
 * @title: SysGroupServiceImpl
 * @description:
 * @company: 润投科技
 * @author: wuys
 * @datetime: 2019-12-18 15:51
 * </pre>
 */
@Service
open class SysGroupServiceImpl : ServiceImpl<SysGroupMapper, SysGroup>(), ISysGroupService {

    @Transactional
    override fun getAllGroups(): List<JSONObject> {
        return baseMapper.getAllGroups()
    }

    @Transactional
    override fun findListByPage(page: Int, pageSize: Int, groupId: Long?): R {
        var pageSize = pageSize
        if (pageSize == 0) {
            pageSize = Constants.DEFAULT_PAGESIZE
        }
        val dictPage = Page<SysGroup>(page.toLong(), pageSize.toLong())
        var sysDictIPage: IPage<SysGroup>? = null
        val lambdaQueryWrapper = Wrappers.query<SysGroup>()
        sysDictIPage = baseMapper.selectPage(dictPage, lambdaQueryWrapper.select(SysGroup::class.java) { i -> true })
        return R.ok(sysDictIPage)
    }

    override fun deleteByIds(ids: List<Long>): R {
        val result = this.baseMapper.deleteBatchIds(ids)
        return if (result > 0) {
            R.ok()
        } else R.error()
    }

    override fun getById(id: Long?): R {
        val e = super.getById(id)
        return R.ok(e)
    }

    override fun saveSysGroup(entity: SysGroup): R {
        val r = this.save(entity)
        return if (r) {
            R.ok()
        } else R.error()
    }

    override fun updateSysGroupById(entity: SysGroup): R {
        val r = this.updateById(entity)
        return if (r) {
            R.ok()
        } else R.error()
    }

    override fun selectSysGroupList(page: Int?, pageSize: Int?, name: String?): IPage<SysGroup> {
        var pageSize = pageSize
        if (pageSize == 0) {
            pageSize = Constants.DEFAULT_PAGESIZE
        }
        val groupPage = Page<SysGroup>(page!!.toLong(), pageSize!!.toLong())
        var sysGroupIPage: IPage<SysGroup>? = null
        if (StringUtils.isNotEmpty(name)) {
            sysGroupIPage = baseMapper.selectPage(groupPage, Wrappers.query<SysGroup>().like("group_name", name)
                    .eq("is_valid", YesOrNoEnum.YES.value).select(SysGroup::class.java) { true })
        } else {
            sysGroupIPage = baseMapper.selectPage(groupPage, Wrappers.query<SysGroup>()
                    .eq("is_valid", YesOrNoEnum.YES.value).select(SysGroup::class.java) { true })
        }
        return sysGroupIPage
    }

    override fun getGroupList(): List<JSONObject> {
        //		try {
        //			return GuavaCacheManager.INSTANCE.SHOP_CACHE.get(Constants.GROUP_ALL_KEY, new Callable<List<JSONObject>>() {
        //				@Override
        //				public List<JSONObject> call() throws Exception {
        //					return baseMapper.getAllShops();
        //				}
        //			});
        //		} catch (ExecutionException e) {
        //			LOG.error("加载全部店铺列表出错!",e);
        //		}
        //		return Collections.EMPTY_LIST;
        return baseMapper.getAllGroups()
    }

    override fun getSysGroupList(): List<SysGroup> {
        return baseMapper.selectList(Wrappers.lambdaQuery())
    }
}