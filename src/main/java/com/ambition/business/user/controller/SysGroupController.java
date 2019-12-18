package com.ambition.business.user.controller;

import com.ambition.business.user.domain.SysGroup;
import com.ambition.business.user.domain.SysUser;
import com.ambition.business.user.service.ISysGroupService;
import com.ambition.common.annotations.AddSysLog;
import com.ambition.common.annotations.CurrentUser;
import com.ambition.common.annotations.LoginedUser;
import com.ambition.common.util.R;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 后台分组表 前端控制器
 * </p>
 *
 * @author wuys
 * @since 2019-12-11
 */
@RestController
@RequestMapping("/sys-group")
public class SysGroupController {

	private static final Logger LOG = LoggerFactory.getLogger(SysGroupController.class);

	@Autowired
	private ISysGroupService service;

	/**
	 * 查询分页数据
	 */
	@ApiOperation(value = "查询分页数据")
	@GetMapping(value = "/list")
	@LoginedUser
	@AddSysLog(descrption = "分页查询SysGroup列表")
	public R findListByPage(@CurrentUser @ApiIgnore SysUser sysUser, @RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "pageSize", defaultValue = "20") int pageSize) {
		return service.findListByPage(page, pageSize, sysUser.getGroupId());
	}


	/**
	 * 根据id查询
	 */
	@ApiOperation(value = "根据id查询数据")
	@GetMapping(value = "/getById")
	@LoginedUser
	@AddSysLog(descrption = "根据id查询SysGroup")
	public R getById(@RequestParam("pkid") Long pkid) {
		return service.getById(pkid);
	}

	/**
	 * 新增
	 */
	@ApiOperation(value = "新增数据")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@LoginedUser
	@AddSysLog(descrption = "新增SysGroup")
	public R save(@CurrentUser @ApiIgnore SysUser sysUser, @RequestBody @Valid SysGroup entity) {
		return service.saveSysGroup(entity);
	}

	/**
	 * 删除
	 */
	@ApiOperation(value = "删除数据")
	@PostMapping(value = "/del")
	@LoginedUser
	@AddSysLog(descrption = "根据id删除SysGroup")
	public R delete(@RequestParam("ids") List<Long> ids) {
		return service.deleteByIds(ids);
	}

	/**
	 * 修改
	 */
	@ApiOperation(value = "更新数据")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@LoginedUser
	@AddSysLog(descrption = "更新SysGroup")
	public R update(@CurrentUser @ApiIgnore SysUser sysUser, @RequestBody @Valid SysGroup entity) {
		return service.updateSysGroupById(entity);
	}
}
