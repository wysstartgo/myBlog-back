package com.ambition.common.plugin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * <pre>
 *  
 *
 * @title : 自定义Mapper
 * @description :
 * @company :
 * @author : 唐宋
 * @time: 2017年8月24日 下午8:36:09
 * </pre>
 */
public class CustomJacksonObjectMapper extends ObjectMapper {

    /** @Fields serialVersionUID: */
    private static final long serialVersionUID = 1L;

    public CustomJacksonObjectMapper() {
        super();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        registerModule(simpleModule);
    }

}
