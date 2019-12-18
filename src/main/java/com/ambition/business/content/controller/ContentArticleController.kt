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
@RequestMapping("/{origin}/content")
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

}
