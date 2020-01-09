package com.ambition.business.log.controller;

import com.ambition.business.log.domain.SysLog;
import com.ambition.business.log.service.ISysLogService;
import com.ambition.business.user.domain.SysUser;
import com.ambition.common.annotations.AddSysLog;
import com.ambition.common.annotations.CurrentUser;
import com.ambition.common.annotations.LoginedUser;
import com.ambition.common.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 系统日志 前端控制器
 * </p>
 *
 * @author wuys
 * @since 2019-12-11
 */
@RestController
@RequestMapping("/syslog")
@Api(tags = "系统日志")
public class SysLogController {

	private static final Logger LOG = LoggerFactory.getLogger(SysLogController.class);

	@Autowired
	private ISysLogService service;

	@GetMapping(value = "/list")
	//@AddSysLog
	@RequiresPermissions("syslog:list")
	@LoginedUser
	@ApiOperation(value = "查询系统日志",notes = "查询系统日志")
	public R selectList(@CurrentUser @ApiIgnore SysUser sysUser, @RequestParam Integer page, @RequestParam Integer pageSize){
		return R.ok(service.findListByPage(page,pageSize,sysUser.getGroupId()));
	}


	/**
	 * 根据id查询
	 */
	@ApiOperation(value = "根据id查询数据")
	@GetMapping(value = "/getById")
	@LoginedUser
	@AddSysLog(descrption = "根据id查询SysLog")
	public R getById(@RequestParam("pkid") Long pkid) {
		return service.getById(pkid);
	}

	/**
	 * 新增
	 */
	@ApiOperation(value = "新增数据")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@LoginedUser
	@AddSysLog(descrption = "新增SysLog")
	public R save(@CurrentUser @ApiIgnore SysUser sysUser, @RequestBody @Valid SysLog entity) {
		return service.saveSysLog(entity);
	}

	/**
	 * 删除
	 */
	@ApiOperation(value = "删除数据")
	@PostMapping(value = "/del")
	@LoginedUser
	@AddSysLog(descrption = "根据id删除SysLog")
	public R delete(@RequestParam("ids") List<Long> ids) {
		return service.deleteByIds(ids);
	}

	/**
	 * 修改
	 */
	@ApiOperation(value = "更新数据")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@LoginedUser
	@AddSysLog(descrption = "更新SysLog")
	public R update(@CurrentUser @ApiIgnore SysUser sysUser, @RequestBody @Valid SysLog entity) {
		return service.updateSysLogById(entity);
	}
}
