package ru.tfs.hw.spring_core.task2.timed.metric;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.tfs.hw.spring_core.task2.timed.Timed;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
class TimedBeanPostProcessor implements BeanPostProcessor {
    private final Map<String, Object> container = new HashMap<>();
    private final MetricsRepository metricsRepository;

    public TimedBeanPostProcessor(@Lazy MetricsRepository metricsRepository) {
        this.metricsRepository = metricsRepository;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        if (clazz.isAnnotationPresent(Timed.class)) {
            container.put(beanName, bean);
            return bean;
        }

        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Timed.class)) {
                container.put(beanName, bean);
                break;
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (container.containsKey(beanName)) {
            Class<?> clazz = container.get(beanName).getClass();
            return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), (proxy, method, params) -> {
                if (clazz.isAnnotationPresent(Timed.class)
                        || method.isAnnotationPresent(Timed.class)
                        || clazz.getMethod(method.getName(), method.getParameterTypes()).isAnnotationPresent(Timed.class)) {

                    long before = System.nanoTime();
                    Object result = method.invoke(bean, params);
                    long after = System.nanoTime();

                    metricsRepository.save(MetricUnit.builder()
                            .methodName(bean.getClass().getName() + "." + method.getName())
                            .executionTime(after - before)
                            .createTime(LocalDateTime.now())
                            .build());

                    return result;
                }
                return method.invoke(bean, params);
            });
        }
        return bean;
    }
}
