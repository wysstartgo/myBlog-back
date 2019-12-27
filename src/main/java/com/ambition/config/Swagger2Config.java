package com.ambition.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * @Classname Swagger2Config
 * @Description Swagger2 配置
 * @Author
 * @Date 2019-06-18 14:37
 * @Version 1.0
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

//    @Value("${jwt.header}")
//    private String tokenHeader;

//    @Bean
//    public Docket createRestApi() {
////        ParameterBuilder ticketPar = new ParameterBuilder();
////        ArrayList<Parameter> pars = Lists.newArrayList();
////        ticketPar.name(tokenHeader).description("token")
////                .modelRef(new ModelRef("string"))
////                .parameterType("header")
////                .defaultValue("Bearer ")
////                .required(true)
////                .build();
////        pars.add(ticketPar.build());
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .select()
//                //选择controller包
//                .apis(RequestHandlerSelectors.basePackage("com.ambition.business.controller"))
//                .paths(PathSelectors.any())
//                .build().securitySchemes();
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                //自定义信息可按需求填写
//                .title("药品管理系统使用Swagger2构建RESTFul APIs")
//                .description("测试")
//                .termsOfServiceUrl("http://www.52lhd.com")
//                .contact(new Contact("永生","http://www.52lhd.com","wysstartgo@163.com"))
//                .version("1.0")
//                .build();
//    }

	@Bean
	public Docket platformApi() {

		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).forCodeGeneration(true)
				.select().apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				.apis(RequestHandlerSelectors.basePackage("com.ambition.business"))
				.paths(regex("^.*(?<!error)$"))
				.build()
				.securitySchemes(securitySchemes())
				.securityContexts(securityContexts());
	}

	private List<ApiKey> securitySchemes() {
		List<ApiKey> apiKeyList= new ArrayList();
		apiKeyList.add(new ApiKey("x-auth-token", "x-auth-token", "header"));
		return apiKeyList;
	}

	private List<SecurityContext> securityContexts() {
		List<SecurityContext> securityContexts=new ArrayList<>();
		securityContexts.add(
				SecurityContext.builder()
						.securityReferences(defaultAuth())
						.forPaths(regex("^(?!auth).*$"))
						.build());
		return securityContexts;
	}

	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		List<SecurityReference> securityReferences=new ArrayList<>();
		securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
		return securityReferences;
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("feitian-API").description("©2019 Copyright. Powered By feitian.")
				// .termsOfServiceUrl("")
				.contact(new Contact("feitian", "", "wysstartgo@163.com")).license("Apache License Version 2.0")
				.licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE").version("2.0").build();
	}


}
