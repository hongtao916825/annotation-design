package com.strategy.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Strategy {

    /**
     * 同一系列策略的集合
     */
    String id();

    /**
     * 策略执行时机
     */
    String type();



}
