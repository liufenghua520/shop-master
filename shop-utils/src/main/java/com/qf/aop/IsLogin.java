package com.qf.aop;

import java.lang.annotation.*;

/**
 * 自定义的注解，判断是否登陆以及是否强制登陆
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IsLogin {
    boolean mustLogin() default false;
}
