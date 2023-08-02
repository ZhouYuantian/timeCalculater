package com.timecalculater.utils;

import com.timecalculater.Main;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringUtil {
    private static ApplicationContext context =new AnnotationConfigApplicationContext(Main.class);
    public static Object getBean(String name)
    {
        return context.getBean(name);
    }
    public static <T> T getBean(Class<T> clazz)
    {
        return context.getBean(clazz);
    }
}
