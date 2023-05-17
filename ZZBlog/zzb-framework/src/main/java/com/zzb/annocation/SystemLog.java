package com.zzb.annocation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: zhangzongbin
 * @date: 2023/3/18 - 1:44
 * @mail: 2218722664@qq.com
 * @info:
 */
//保持到那个阶段
@Retention(RetentionPolicy.RUNTIME)
//注解可以加到方法上面
@Target(ElementType.METHOD)
public @interface SystemLog {
    String businessName();
}
