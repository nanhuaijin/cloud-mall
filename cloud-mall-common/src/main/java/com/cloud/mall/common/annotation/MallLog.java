package com.cloud.mall.common.annotation;

import java.lang.annotation.*;

/**
 * 注意：该注解无法作用于入出参是流对象以及一切无法被JSON序列化的方法
 * @author : breeze
 * @date : 2020/10/17
 * @description : 全局日志收集注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MallLog {
}
