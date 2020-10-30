package com.example.strategy;

import com.strategy.annotation.Strategy;
import com.strategy.annotation.StrategyBean;
import com.strategy.context.StrategyDesign;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

/**
 * @author hongtao
 * @date 2020/10/30 12:23 上午
 */
public class Test {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.scan("com");
        annotationConfigApplicationContext.refresh();
        annotationConfigApplicationContext.start();
        StrategyDesign.doMethod("2","test2");
        System.out.println(StrategyDesign.doMethod("3", "test3_1", String.class));
        System.out.println(StrategyDesign.doMethod("4","test3_1",String.class, "1", "2"));
    }



}

/**
 * @author hongtao
 * @date 2020/10/30 12:06 上午
 */
@Component
@StrategyBean
class StrategyTest {

    @Strategy(id = "1", type = "test1")
    public void Strategy1_1(){
        System.out.println("1-test1");
    }

    @Strategy(id = "1", type = "test2")
    public void Strategy1_2(){
        System.out.println("1-test2");
    }

    @Strategy(id = "2", type = "test1")
    public void Strategy2_1(){
        System.out.println("2-test2");
    }

    @Strategy(id = "2", type = "test2")
    public void Strategy2_2(){
        System.out.println("2-test2");
    }

    @Strategy(id = "3", type = "test3_1")
    public String Strategy3_1(){
        return "test3_1";
    }

    @Strategy(id = "3", type = "test3_2")
    public String Strategy3_2(){
        return "test3_2";
    }

    @Strategy(id = "4", type = "test3_1")
    public String Strategy4_1(String arg1, String arg2){
        return arg1 + arg2;
    }

    @Strategy(id = "3", type = "test3_2")
    public String Strategy4_2(String arg1, String arg2){
        return arg1;
    }
}


