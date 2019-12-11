package com.ambition.common.util.excel;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ExcelListener extends AnalysisEventListener {

    private static final Logger LOG = LoggerFactory.getLogger(ExcelListener.class);

    private List<Object> dataList = new ArrayList<>();

    /**
     * 通过 AnalysisContext 对象还可以获取当前 sheet，当前行等数据
     */
    @Override
    public void invoke(Object object, AnalysisContext context) {
        //跳过数据不全的
        if(!checkObjAllFieldsIsNull(object)) {
            dataList.add(object);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        //do something
    }

    private static final String SERIAL_VERSION_UID = "serialVersionUID";

    /**
     * 判断对象中属性值是否全为空
     */
    private static boolean checkObjAllFieldsIsNull(Object object) {
        if (null == object) {
            return true;
        }
        try {
            for (Field f : object.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                //只校验带ExcelProperty注解的属性
                ExcelProperty property = f.getAnnotation(ExcelProperty.class);
                if(property == null || SERIAL_VERSION_UID.equals(f.getName())){
                    continue;
                }
                if (f.get(object) != null && MyStringUtils.isNotBlank(f.get(object).toString())) {
                    return false;
                }else {
                    LOG.info(property.value()[0] + ": 该行数据有属性为空，详细信息为:" + f.get(object) + "    将会自动跳过该行!");
                }
            }
        } catch (Exception e) {
            //do something
            LOG.error("导入出错!",e);
        }
        return true;
    }

    public List<Object> getDataList() {
        return dataList;
    }
}