package com.cloud.mall.common.annotation;

import java.lang.annotation.*;

/**
 * @author : breeze
 * @date : 2020/10/17
 * @description : 全局日志收集注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MallLog {
}
