package com.strategy.annotation;

import java.lang.annotation.*;

/**
 * @author hongtao
 * @date 2020/10/29 11:48 下午
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface StrategyBean {
}
