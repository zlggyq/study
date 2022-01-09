package com.zlgg.study.common.elasticsearch;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.converters.DateConverter;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: wzl
 * Date: 2021-12-22
 * Time: 23:03
 * Description:
 */
public class BeanUtil {

    /**
     * 将管理上下文的applicationContext设置成静态变量，供全局调用
     */
    public static ConfigurableApplicationContext applicationContext;

    /*
     * BeanUtils.describe()日期格式
     */
    static {
        System.out.println("-------------------fsdfsdfsfsdf-----------");
        ConvertUtilsBean convertUtils = BeanUtilsBean.getInstance().getConvertUtils();
        DateConverter dateConverter = new DateConverter();
        dateConverter.setPattern("yyyy-MM-dd HH:mm:ss");
        convertUtils.register(dateConverter, String.class);
    }


    private BeanUtil() {
    }

    /**
     * 定义一个获取已经实例化bean的方法
     */
    public static <T> T getBean(Class<T> c) {
        return applicationContext.getBean(c);
    }

    /**
     * Bean转Map, 格式化时间, 去class
     *
     * @author Aylvn
     * @date 2021-02-09
     */
    public static Map<String, String> beanToMap(Object bean) {
        try {
            ConvertUtilsBean convertUtils = BeanUtilsBean.getInstance().getConvertUtils();
            DateConverter dateConverter = new DateConverter();
            dateConverter.setPattern("yyyy-MM-dd HH:mm:ss");
            convertUtils.register(dateConverter, String.class);
            Map<String, String> map = BeanUtils.describe(bean);
            // 移除key为class的对象
            map.remove("class");
            return map;
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }
}
