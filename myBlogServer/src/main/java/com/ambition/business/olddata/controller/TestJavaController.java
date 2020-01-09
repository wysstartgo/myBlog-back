package com.ambition.business.olddata.controller;

import com.ambition.business.user.service.ISysGroupService;
import com.ambition.common.util.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <pre>
 *
 *
 * @title: TestController
 * @description:
 * @company: 润投科技
 * @author: wuys
 * @datetime: 2019-12-25 17:30
 * </pre>
 */
@RestController
@RequestMapping("/testtest")
public class TestJavaController {

	@Resource
	private ISysGroupService sysGroupService;

	@GetMapping("/list")
	@RequiresPermissions("group:add")
	public R test(){
		System.out.println("-------------------------");
		System.out.println(sysGroupService);
		System.out.println("-------------------------");
		return R.ok();
	}
}
