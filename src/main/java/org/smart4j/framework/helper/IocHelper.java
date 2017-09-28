package org.smart4j.framework.helper;

import org.smart4j.framework.annotation.Inject;
import org.smart4j.framework.util.*;

import java.lang.reflect.Field;
import java.util.Map;

public class IocHelper {

    static {
        //获取所有的bean类与bean实例之间的映射关系（简称Bean map)
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (!CollectionUtil.mapIsEmpty(beanMap)) {
            //遍历bean map
            for (Map.Entry<Class<?>, Object> beanEntity : beanMap.entrySet()) {
                Class<?> beanClass = beanEntity.getKey();
                Object beanInstance = beanEntity.getValue();
                //获取bean类定义的所有成员变量(简称Bean Filed)
                Field[] beanFields = beanClass.getDeclaredFields();
                if (ArrayUtil.isNotEmpty(beanFields)) {
                    //遍历Bean field
                    for (Field beanField : beanFields) {
                        //判断当前bean field是否带有Inject注解
                        if (beanField.isAnnotationPresent(Inject.class)) {
                            //在 Bean Map中获取Bean Field对应的实例
                            Class<?> beanFieldClass = beanField.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if (beanFieldInstance != null) {
                                //通过反射初始化BeanField的值
                                ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                            }
                        }
                    }
                }
            }

        }
    }
}
