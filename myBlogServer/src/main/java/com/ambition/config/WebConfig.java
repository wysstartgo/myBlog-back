package com.ambition.config;

import com.ambition.common.plugin.CustomJacksonObjectMapper;
import com.ambition.config.auth.AuthInterceptor;
import com.ambition.config.resolver.CurrentUserMethodArgumentResolver;
import com.ambition.config.resolver.StringToDateConverter;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @title :
 * @description :
 * @copyright :
 * @date : 2019/4/15 16:35
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private AuthInterceptor authInterceptor;


    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {

    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {

    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {

    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToDateConverter());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).addPathPatterns("/**");
//        registry.addInterceptor(new SubmitInterceptor()).
//                addPathPatterns("/**").excludePathPatterns("/aaa/*.ftl", "/aaa/*.html");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**/AdminLTE/**").addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("/**/bootstrap/**").addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("/**/cron/**").addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("/**/flat_ui/**").addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("/**/font-awesome/**").addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("/**/Ionicons/**").addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("/**/iview/**").addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("/**/layer/**").addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("/**/libs/**").addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("/**/*.js").addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("/infoopsys/*.html").addResourceLocations("/WEB-INF/templates/");
//        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {

    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/login.shtml");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        //System.out.println("************************add");
        //registry.addViewController("/").setViewName("forward:/index.html");
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new CurrentUserMethodArgumentResolver());
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {

    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //创建fastJson消息转换器
        StringHttpMessageConverter messageConverter = new StringHttpMessageConverter();
        //创建配置类
        messageConverter.setDefaultCharset(Charset.defaultCharset());
        MediaType jsonUtf8 = MediaType.APPLICATION_JSON_UTF8;
        MediaType xmlUtf8 = MediaType.APPLICATION_XML;
        MediaType textHtml = MediaType.TEXT_HTML;
        List<MediaType> medias = new ArrayList<>();
        medias.add(jsonUtf8);
        medias.add(xmlUtf8);
        medias.add(textHtml);
        messageConverter.setSupportedMediaTypes(medias);
        converters.add(messageConverter);

        //空字段过滤
        MappingJackson2HttpMessageConverter xssConvert = new MappingJackson2HttpMessageConverter();
        CustomJacksonObjectMapper objectMapper = new CustomJacksonObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        List<MediaType> medias2 = new ArrayList<>();
        medias2.add(jsonUtf8);
        medias2.add(textHtml);
        xssConvert.setObjectMapper(objectMapper);
        xssConvert.setSupportedMediaTypes(medias2);
        converters.add(xssConvert);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {

    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {

    }

    @Override
    public Validator getValidator() {
        return null;
    }

    @Override
    public MessageCodesResolver getMessageCodesResolver() {
        return null;
    }
}
