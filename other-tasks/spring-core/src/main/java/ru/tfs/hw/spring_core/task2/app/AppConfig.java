package ru.tfs.hw.spring_core.task2.app;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.tfs.hw.spring_core.task2.timed.config.TimedConfig;

@Configuration
@Import(TimedConfig.class)
@ComponentScan("ru.tfs.hw.spring_core.task2.app")
public class AppConfig {

}
