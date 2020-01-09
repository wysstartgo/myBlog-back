package com.ambition.business.content.controller;

import com.ambition.business.content.domain.ContentPoemPoetry;
import com.ambition.business.content.service.IContentPoemPoetryService;
import com.ambition.business.user.domain.SysUser;
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
 *  前端控制器
 * </p>
 *
 * @author wuys
 * @since 2019-12-11
 */
@RestController
@RequestMapping("/poem-poetry")
public class ContentPoemPoetryController {

     private static final Logger LOG = LoggerFactory.getLogger(ContentPoemPoetryController.class);

     @Autowired
     private IContentPoemPoetryService service;

    /**
    * 查询分页数据
    */
    @ApiOperation(value = "查询分页数据")
    @GetMapping(value = "/list")
    @LoginedUser
    @AddSysLog(descrption = "分页查询ContentPoemPoetry列表")
    public R findListByPage(@CurrentUser@ApiIgnore SysUser sysUser, @RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "pageSize", defaultValue = "20") int pageSize){
      return service.findListByPage(page,pageSize,sysUser.getGroupId());
    }


     /**
     * 根据id查询
     */
     @ApiOperation(value = "根据id查询数据")
     @GetMapping(value = "/detail")
     @LoginedUser
     @AddSysLog(descrption = "根据id查询ContentPoemPoetry")
     public R getById(@RequestParam("id") Long id){
       return service.getById(id);
     }

     /**
     * 新增
     */
     @ApiOperation(value = "新增数据")
     @RequestMapping(value = "/add", method = RequestMethod.POST)
     @LoginedUser
     @AddSysLog(descrption = "新增ContentPoemPoetry")
     public R save(@CurrentUser@ApiIgnore SysUser sysUser,@RequestBody @Valid ContentPoemPoetry entity){
        return service.saveContentPoemPoetry(entity);
     }

     /**
     * 删除
     */
     @ApiOperation(value = "删除数据")
     @RequestMapping(value = "/del",method = RequestMethod.POST)
     @LoginedUser
     @AddSysLog(descrption = "根据id删除ContentPoemPoetry")
     public R delete(@RequestParam("ids") List<Long> ids){
       return service.deleteByIds(ids);
     }

     /**
     * 修改
     */
     @ApiOperation(value = "更新数据")
     @RequestMapping(value = "/update", method = RequestMethod.POST)
     @LoginedUser
     @AddSysLog(descrption = "更新ContentPoemPoetry")
     public R update(@CurrentUser@ApiIgnore SysUser sysUser,@RequestBody @Valid ContentPoemPoetry entity){
       return service.updateContentPoemPoetryById(entity);
     }
}
