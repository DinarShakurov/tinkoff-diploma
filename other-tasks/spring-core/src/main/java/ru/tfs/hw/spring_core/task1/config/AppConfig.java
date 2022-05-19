package ru.tfs.hw.spring_core.task1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;
import static org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS;

@Configuration
@Import({
        RuAppConfig.class,
        EnAppConfig.class
})
@ComponentScan("ru.tfs.hw.spring_core.task1")
public class AppConfig {

    @Bean
    @Scope(scopeName = SCOPE_PROTOTYPE, proxyMode = TARGET_CLASS)
    public DateFormat isoDateFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat;
    }
}
