package com.cloud.mall.common.utils;

import java.util.TimeZone;

/**
 *
 * @author breeze
 * @since 2020-08-19
 */
public class DateUtils {

    static final TimeZone TIME_ZONE = TimeZone.getTimeZone("Asia/Shanghai");

    /**
     * 日期格式yyyy-MM-dd HH:mm:ss字符串常量
     */
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式yyyy-MM-dd字符串常量
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 日期格式yyyyMMdd字符串常量
     */
    public static final String DATE_NORMAL_FORMAT = "yyyyMMdd";

    /**
     * 日期格式yyyy-MM字符串常量
     */
    public static final String MONTH_FORMAT = "yyyyMM";

    /**
     * 时间格式是HH:mm:ss字符串常量
     */
    public static final String TIME_FORMAT = "HH:mm:ss";

    /**
     * 一天的开始与结束
     */
    public static final String DATETIME_FORMAT_START = "yyyy-MM-dd 00:00:00";
    public static final String DATETIME_FORMAT_END = "yyyy-MM-dd 23:59:59";

    /**
     * 日期格式HH:mm:ss字符串常量
     */
    public static final String HOUR_FORMAT = "HH:mm:ss";

}
