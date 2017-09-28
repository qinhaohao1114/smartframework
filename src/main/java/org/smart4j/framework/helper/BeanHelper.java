package org.smart4j.framework.helper;

import org.smart4j.framework.util.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 相当于一个bean实例子
 */
public class BeanHelper {

    /**
     * 定义bean映射（用于存放bean类与bean实例的映射关系）
     */
    private static final Map<Class<?>,Object> BEAN_MAP = new HashMap<Class<?>, Object>();

    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for(Class<?> beanClass : beanClassSet){
           Object obj = ReflectionUtil.newInstance(beanClass);
           BEAN_MAP.put(beanClass,obj);
        }
    }

    /**
     * 获取bean映射
     * @return
     */
    public static Map<Class<?>,Object> getBeanMap(){
        return BEAN_MAP;
    }

    /**
     * 获取bean实例
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> cls){
        if(!BEAN_MAP.containsKey(cls)){
            throw new RuntimeException("can not get bean by class:"+cls);

        }
        return (T)BEAN_MAP.get(cls);
    }
}
