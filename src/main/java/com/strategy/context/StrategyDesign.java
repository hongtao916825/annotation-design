package com.strategy.context;

import com.strategy.annotation.Strategy;
import com.strategy.annotation.StrategyBean;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hongtao
 * @date 2020/10/29 11:33 下午
 */
@Component
public class StrategyDesign implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    private static Map<String, RegistryBean> strategyMap = new HashMap<>();

    @PostConstruct
    public void initStrategy(){
        Map<String, Object> strategyBeanMap = applicationContext.getBeansWithAnnotation(StrategyBean.class);
        strategyBeanMap.forEach((beanName, bean) -> {
            Method[] methods = bean.getClass().getMethods();
            Arrays.stream(methods)
                    .filter(method -> method.isAnnotationPresent(Strategy.class))
                    .forEach(method -> {
                        Strategy strategy = method.getAnnotation(Strategy.class);
                        String id = strategy.id();
                        String type = strategy.type();
                        RegistryBean registryBean = new RegistryBean(beanName, method.getName(), method);
                        if(!strategyMap.containsKey(id + "-" + type)){
                            strategyMap.put(id + "-" +type, registryBean);
                        }
                    });
        });
    }

    public static void doMethod(String id, String type, Object... objs) throws InvocationTargetException, IllegalAccessException {
        RegistryBean registryBean = strategyMap.get(id + "-" + type);
        Assert.notNull(registryBean);
        Object bean = applicationContext.getBean(registryBean.getBeanName());
        registryBean.getMethod().setAccessible(true);
        registryBean.getMethod().invoke(bean, objs);
    }

    public static <T> T doMethod(String id, String type, Class<T> T,  Object... objs) throws InvocationTargetException, IllegalAccessException {
        RegistryBean registryBean = strategyMap.get(id + "-" + type);
        Assert.notNull(registryBean);
        Object bean = applicationContext.getBean(registryBean.getBeanName());
        registryBean.getMethod().setAccessible(true);
        Object result = registryBean.getMethod().invoke(bean, objs);
        if(result != null){
           return (T)result;
        }
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        StrategyDesign.applicationContext = applicationContext;
    }


    class  RegistryBean{

        private String beanName;

        private String methodName;

        private Method method;


        public RegistryBean(String beanName, String methodName, Method method) {
            this.beanName = beanName;
            this.methodName = methodName;
            this.method = method;
        }

        public Method getMethod() {
            return method;
        }

        public void setMethod(Method method) {
            this.method = method;
        }

        public String getBeanName() {
            return beanName;
        }

        public void setBeanName(String beanName) {
            this.beanName = beanName;
        }

        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }
    }

}
