package com.ambition.poetry;

import com.alibaba.fastjson.JSONObject;
import com.ambition.business.olddata.domain.PoemsAuthor;
import com.ambition.business.olddata.service.IPoemsAuthorService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.google.common.base.Function;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *
 *
 * @title: JavaTest
 * @description:
 * @company: 润投科技
 * @author: wuys
 * @datetime: 2019-12-24 16:45
 * </pre>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JavaTest {

	@Resource
	private  IPoemsAuthorService poemsAuthorService;



	@Test
	public void getPoemsByAuthor(){
//		IntStream.range(0,1).forEach(abc -> {
//
//		});

//        val lambdaQuery = Wrappers.lambdaQuery<PoemsAuthor>()
//        lambdaQuery.eq(PoemsAuthor::getName,"杜甫")
		//lambdaQuery.eq({ poemsAuthor: PoemsAuthor -> poemsAuthor.name } , "杜甫")
		//对总条数进行分页处理
//        val selectOne = poemsAuthorService!!.baseMapper.selectOne(Wrappers.lambdaQuery<PoemsAuthor>().eq({ PoemsAuthor::javaClass::name }, "杜甫"))
//        val poemsAuthor = poemsAuthorService!!.getOne(Wrappers.lambdaQuery<PoemsAuthor>())
		LambdaQueryWrapper<PoemsAuthor> queryWrapper = Wrappers.<PoemsAuthor>lambdaQuery();
		SFunction<PoemsAuthor, String> getName = PoemsAuthor::getName;
		//@Note 这里如果是新建一个对象而不是使用lambda的写法，在myibatis-plus的内部com.baomidou.mybatisplus.core.toolkit.support.SerializedLambda.resolve中无法识别，将会报错
		SFunction<PoemsAuthor, String> sFunction = new SFunction<PoemsAuthor, String>() {
			@Override
			public String apply(PoemsAuthor poemsAuthor) {
				return poemsAuthor.getName();
			}
		};
		SFunction<PoemsAuthor, Integer> getId = PoemsAuthor::getId;
		queryWrapper.eq(sFunction,"苏轼");
		PoemsAuthor poemsAuthor = poemsAuthorService.getOne(queryWrapper);
		List<PoemsAuthor> list = new ArrayList<>();
		Function<CharSequence, Boolean> isBlank = StringUtils::isBlank;
		SFunction<CharSequence, Boolean> isBlank1 = StringUtils::isBlank;
		list.stream().forEach(poemsAuthor1 -> {

		});
		System.out.println(JSONObject.toJSONString(poemsAuthor));

	}
}
