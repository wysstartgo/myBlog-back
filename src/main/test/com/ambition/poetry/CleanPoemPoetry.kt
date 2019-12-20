package com.ambition.poetry

import com.github.houbb.opencc4j.util.ZhConverterUtil
import org.junit.Test
import org.springframework.boot.test.context.SpringBootTest

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

@SpringBootTest
class CleanPoemPoetry {

    @Test
    fun testConverter(){
        val convertToSimple = ZhConverterUtil.convertToSimple("鹊踏枝・蝶恋花")
        val convertToSimple1 = ZhConverterUtil.convertToSimple("闲把鸳衾横枕。|损眉尖、泪痕红沁。|花时良夜不归来，忍频听、漏移清禁。|一饷无言都未寝。|忆当初、是谁先恁。|及至如今，教人成病，风流万般徒甚。", false)
        println(convertToSimple)
        println("=====================")
        println(convertToSimple1)
    }


}

