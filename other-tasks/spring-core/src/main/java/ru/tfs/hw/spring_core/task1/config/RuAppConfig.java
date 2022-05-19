package ru.tfs.hw.spring_core.task1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;
import static org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS;

@Configuration
@Profile("ru & !en")
public class RuAppConfig {

    @Bean
    @Scope(scopeName = SCOPE_PROTOTYPE, proxyMode = TARGET_CLASS)
    public DateFormat localedDateFormat() {
        return new SimpleDateFormat("EEEE, dd MMMM, yyyy", Locale.forLanguageTag("ru"));
    }
}
