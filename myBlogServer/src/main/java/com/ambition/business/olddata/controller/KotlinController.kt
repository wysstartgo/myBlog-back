package com.ambition.business.olddata.controller

import com.ambition.common.util.R
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * <pre>
 *
 *
 * @title: KotlinController
 * @description:
 * @company: 润投科技
 * @author: wuys
 * @datetime: 2019-12-26 15:34
 * </pre>
 */
@RestController
@RequestMapping("/testkt")
class KotlinController {

    @GetMapping("/list")
    fun test(): R {
        return R.ok()
    }

}