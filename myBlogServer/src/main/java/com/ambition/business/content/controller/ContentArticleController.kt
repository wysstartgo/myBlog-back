package com.ambition.business.content.controller

import com.ambition.business.content.service.IContentArticleService
import com.ambition.business.user.domain.SysUser
import com.ambition.common.annotations.AddSysLog
import com.ambition.common.annotations.CurrentUser
import com.ambition.common.annotations.LoginedUser
import com.ambition.common.util.R
import io.swagger.annotations.ApiOperation
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.annotations.ApiIgnore
import com.ambition.business.content.domain.ContentArticle
import javax.validation.Valid
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMethod



/**
 * <pre>
 *
 *
 * @title: ContentArticleController
 * @description:
 * @author: wuys
 * @datetime: 2019-12-18 9:41
 * </pre>
 */
@RestController
@RequestMapping("/article")
class ContentArticleController(
        @Autowired private val service: IContentArticleService
) {

    private companion object{
        val LOG = LoggerFactory.getLogger(ContentArticleController::class.java)
    }

    @ApiOperation(value = "查询分页数据")
    @GetMapping("/list")
    @LoginedUser
    @AddSysLog(descrption = "分页查询ContentArticle列表")
    fun findListByPage(@CurrentUser @ApiIgnore sysUser:SysUser, @RequestParam(name = "page", defaultValue = "1") page:Int, @RequestParam(name = "pageSize", defaultValue = "20") pageSize:Int):R {
        LOG.info("find list by page!")
        return service.findListByPage(page,pageSize,sysUser.groupId)
    }

    /**
     * 根据id查询
     */
    @ApiOperation(value = "根据id查询数据")
    @GetMapping(value = ["/detail"])
    @LoginedUser
    @AddSysLog(descrption = "根据id查询ContentArticle")
    fun getById(@RequestParam("id") id: Long?): R {
        return service.getById(id)
    }

    /**
     * 新增
     */
    @ApiOperation(value = "新增数据")
    @RequestMapping(value = ["/add"], method = [RequestMethod.POST])
    @LoginedUser
    @AddSysLog(descrption = "新增ContentArticle")
    fun save(@CurrentUser @ApiIgnore sysUser: SysUser, @RequestBody @Valid entity: ContentArticle): R {
        return service.saveContentArticle(entity)
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除数据")
    @RequestMapping(value = ["/del"], method = [RequestMethod.POST])
    @LoginedUser
    @AddSysLog(descrption = "根据id删除ContentArticle")
    fun delete(@RequestParam("ids") ids: List<Long>): R {
        return service.deleteByIds(ids)
    }

    /**
     * 修改
     */
    @ApiOperation(value = "更新数据")
    @RequestMapping(value = ["/update"], method = [RequestMethod.POST])
    @LoginedUser
    @AddSysLog(descrption = "更新ContentArticle")
    fun update(@CurrentUser @ApiIgnore sysUser: SysUser, @RequestBody @Valid entity: ContentArticle): R {
        return service.updateContentArticleById(entity)
    }

}
