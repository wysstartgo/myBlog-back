package com.ambition.poetry

import com.alibaba.fastjson.JSONObject
import com.ambition.business.content.domain.ContentAuthor
import com.ambition.business.content.domain.ContentBase
import com.ambition.business.content.domain.ContentPoemPoetry
import com.ambition.business.content.service.IContentAuthorService
import com.ambition.business.content.service.IContentBaseService
import com.ambition.business.content.service.IContentPoemPoetryService
import com.ambition.business.olddata.domain.PoemsAuthor
import com.ambition.business.olddata.domain.PoemsInfo
import com.ambition.business.olddata.service.IPoemsAuthorService
import com.ambition.business.olddata.service.IPoemsInfoService
import com.ambition.business.user.service.ISysGroupService
import com.ambition.business.user.service.ISysUserService
import com.ambition.common.constants.Constants
import com.ambition.common.enums.ContentCategoryEnum
import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.github.houbb.opencc4j.util.ZhConverterUtil
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.util.*
import javax.annotation.Resource


/**
 * <pre>
 *
 *
 * @title: CleanPoemPoetry
 * @description:
 * @author: wuys
 * @datetime: 2019-12-20 11:42
 * </pre>
 */
@RunWith(SpringRunner::class)
@SpringBootTest
class CleanPoemPoetry {

    @Resource
    private var poemsInfoService: IPoemsInfoService? = null

    @Resource
    private var poemsAuthorService: IPoemsAuthorService? = null

    @Resource
    private val contentPoemPoetryService: IContentPoemPoetryService? = null

    @Resource
    private val contentBaseService: IContentBaseService? = null

    @Resource
    private val contentAuthorService: IContentAuthorService? = null

    @Resource
    private val sysUserService: ISysUserService? = null

    @Resource
    private val sysGroupService: ISysGroupService? = null

    @Test
    fun testConverter() {
        //println(poemsInfoService)
        val convertToSimple = ZhConverterUtil.convertToSimple("鹊踏枝・蝶恋花")
        val convertToSimple1 = ZhConverterUtil.convertToSimple("闲把鸳衾横枕。|损眉尖、泪痕红沁。|花时良夜不归来，忍频听、漏移清禁。|一饷无言都未寝。|忆当初、是谁先恁。|及至如今，教人成病，风流万般徒甚。", false)
        println(convertToSimple)
        println("=====================")
        println(convertToSimple1)

    }

    @Test
    fun testAutowire() {
        println("------------------------")
        println(sysUserService)
        println("------------------------")
        println(sysGroupService)
        println("------------------------")
    }

    /**
     * 唐诗
     */
    @Test
    fun getPoetryByAuthor() {

    }

    /**
     * 宋词
     */
    @Test
    fun getPoemsByAuthor() {
//        val function : SFunction<PoemsAuthor, String> = {p:PoemsAuthor -> p.name}

//        val sum = { a: Int, b: Int -> a + b }
        //val getName = SFunction<PoemsAuthor, String> { it.name }
//        val sFunction = SFunction<PoemsAuthor, String> { poemsAuthor -> poemsAuthor.name }
        //val byNickName : SFunction<SysUser,String> = SysUser::getNickName
        //会被idea kotlin编译器识别成KFunction1
//        val lambdaQuery = Wrappers.lambdaQuery<SysUser>()
//        lambdaQuery.eq(byNickName,"admin")
//        sysUserService!!.getOne(lambdaQuery)
//        Wrappers.query<PoemsAuthor>().eq
        val wrapper = Wrappers.query<PoemsAuthor>()
        wrapper.eq("name", "苏轼")
        val poemsAuthor = poemsAuthorService!!.getOne(wrapper)
        System.out.println(JSONObject.toJSONString(poemsAuthor))
//        val lambdaQuery = Wrappers.lambdaQuery<PoemsAuthor>()
//        lambdaQuery.eq(PoemsAuthor::getName,"杜甫")
//        lambdaQuery.eq({ poemsAuthor: PoemsAuthor -> poemsAuthor.name } , "杜甫")
//        val selectOne = poemsAuthorService!!.baseMapper.selectOne(Wrappers.lambdaQuery<PoemsAuthor>().eq({ PoemsAuthor::javaClass::name }, "杜甫"))
//        val poemsAuthor = poemsAuthorService!!.getOne(Wrappers.lambdaQuery<PoemsAuthor>())
//        val poemsAuthor = poemsAuthorService!!.getOne(Wrappers.lambdaQuery<PoemsAuthor>().eq({PoemsAuthor::javaClass::name},"杜甫"))
        val queryWrapper = Wrappers.query<PoemsInfo>()
        queryWrapper.eq("author_id", poemsAuthor.id)
        val list = poemsInfoService!!.list(queryWrapper)
        println(list.size)
        val contentAuthor = ContentAuthor()

        contentAuthor.authorName = poemsAuthor.name
        contentAuthor.authorIntroduction = poemsAuthor.introL
        contentAuthorService!!.saveContentAuthor(contentAuthor)
        list.stream().forEach { t -> print(t.author) }
        list.stream().forEach { a -> print(a.author) }


        for (poemsInfo in list) {
            //println(JSONObject.toJSONString(poemsInfo))
            //println(poemsInfo.title)
            val authorId = contentAuthor.id
            val contentPoemPoetry = ContentPoemPoetry()
            contentPoemPoetry.createTime = Date()
            contentPoemPoetry.userId = Constants.SYS_ID
            contentPoemPoetry.title = poemsInfo.title
            val index = poemsInfo.content.indexOf("|")
            if (index == -1) {
                println(poemsInfo.title + "==================" + poemsInfo.id)
                continue
            }
            contentPoemPoetry.brief = poemsInfo.content.substring(0, index)
//            contentPoemPoetry.contentComplex = poemsInfo.content
            contentPoemPoetry.content = ZhConverterUtil.convertToSimple(poemsInfo.content)
            contentPoemPoetry.author = poemsAuthor.name
            contentPoemPoetry.authorId = authorId
            contentPoemPoetry.userName = "再回首"
            contentPoemPoetry.stores = 0L
            contentPoemPoetry.views = 0L
            contentPoemPoetry.zans = 0L
            contentPoemPoetryService!!.saveContentPoemPoetry(contentPoemPoetry)
            //中间表
            val contentBase = ContentBase()
            contentBase.author = poemsAuthor.name
            contentBase.brief = contentPoemPoetry.brief
            contentBase.contentCategoryId = ContentCategoryEnum.Shongchi.code!!.toLong()
            contentBase.contentCategoryName = ContentCategoryEnum.Shongchi.msg
            contentBase.contentId = contentPoemPoetry.id
            contentBase.createTime = Date()
            contentBase.title = contentPoemPoetry.title
            contentBase.userId = contentPoemPoetry.userId
            contentBase.userName = contentPoemPoetry.userName
            contentBaseService!!.saveContentBase(contentBase)
        }

    }

    @Test
    fun getPoemsByPageList() {
        val count = poemsInfoService!!.count()
        val pageNum = 20
        var pageSize = 0
        if (count % pageNum == 0) {
            pageSize = count / pageNum
        } else {
            pageSize = count / pageNum + 1
        }
        for (page in 1..pageSize) {
            val dictPage = Page<PoemsInfo>(page.toLong(), pageNum.toLong())
            //分页查询
            val iPage = poemsInfoService!!.page(dictPage)
            val records = iPage.records
            for (poemsInfo in records) {
                val lambdaQuery = Wrappers.query<PoemsAuthor>()
                lambdaQuery.eq("anthorId", poemsInfo.authorId)
                //对总条数进行分页处理
                val poemsAuthor = poemsAuthorService!!.getOne(lambdaQuery)
                val contentAuthor = ContentAuthor()

                contentAuthor.authorName = poemsAuthor.name
                contentAuthor.authorIntroduction = poemsAuthor.introL
                contentAuthorService!!.saveContentAuthor(contentAuthor)
                val authorId = contentAuthor.id
                val contentPoemPoetry = ContentPoemPoetry()
                contentPoemPoetry.createTime = Date()
                contentPoemPoetry.userId = Constants.SYS_ID
                contentPoemPoetry.title = poemsInfo.title
                val index = poemsInfo.content.indexOf("|")
                contentPoemPoetry.brief = poemsInfo.content.substring(0, index)
                contentPoemPoetry.contentComplex = poemsInfo.content
                contentPoemPoetry.content = ZhConverterUtil.convertToSimple(poemsInfo.content)
                contentPoemPoetry.author = poemsAuthor.name
                contentPoemPoetry.authorId = authorId
                contentPoemPoetry.userName = "霓漫天"
                contentPoemPoetry.stores = 0L
                contentPoemPoetry.views = 0L
                contentPoemPoetry.zans = 0L
                contentPoemPoetryService!!.saveContentPoemPoetry(contentPoemPoetry)
                //中间表
                val contentBase = ContentBase()
                contentBase.author = poemsAuthor.name
                contentBase.brief = contentPoemPoetry.brief
                contentBase.contentCategoryId = ContentCategoryEnum.Tangshi.code!!.toLong()
                contentBase.contentCategoryName = ContentCategoryEnum.Tangshi.msg
                contentBase.contentId = contentPoemPoetry.id
                contentBase.createTime = Date()
                contentBase.title = contentPoemPoetry.title
                contentBase.userId = contentPoemPoetry.userId
                contentBase.userName = contentPoemPoetry.userName
                contentBaseService!!.saveContentBase(contentBase)

            }
        }

    }


}

