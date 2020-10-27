package com.cloud.mall.user.aspect;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author : breeze
 * @date : 2020/10/17
 * @description : 日志收集切面类
 */
@Aspect
@Component
public class MallLogAspect {
    public static final Logger logger = LoggerFactory.getLogger(MallLogAspect.class);

    /**
     * AOP环绕通知四要素
     * joinPoint.proceed(args);执行业务方法
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("@annotation(com.cloud.mall.common.annotation.MallLog)")
    public Object LogCollectionAspect(ProceedingJoinPoint joinPoint) throws Throwable {

        //最终返回结果
        Object result = null;
        //获取当前类
        String className = joinPoint.getTarget().getClass().getName();
        //获取连接点签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取方法名
        String methodName = signature.getMethod().getName();
        //获取方法参数
        Object[] args = joinPoint.getArgs();

        //入参日志
        logger.info(className + "." + methodName+"：入参");
        logger.info("begin");
        logger.info(JSON.toJSONString(args));
        logger.info("end");

        //执行原方法
        result = joinPoint.proceed(args);

        //出参日志
        logger.info(className + "." + methodName+"：出参");
        logger.info("begin");
        logger.info(JSON.toJSONString(result));
        logger.info("end");

        return result;
    }
}
