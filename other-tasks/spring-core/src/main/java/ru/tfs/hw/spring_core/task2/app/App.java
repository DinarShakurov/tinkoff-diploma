package ru.tfs.hw.spring_core.task2.app;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import ru.tfs.hw.spring_core.task2.timed.metric.MethodMetricStat;
import ru.tfs.hw.spring_core.task2.timed.metric.MetricStatProvider;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class App {

    private final MyService myService;
    private final MetricStatProvider metricStatProvider;

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        App app = applicationContext.getBean(App.class);
        app.start();
    }

    public void start() throws InterruptedException {
        String result = myService.doWork();
        List<MethodMetricStat> list = metricStatProvider.getTotalStatForPeriod(LocalDateTime.now().minusMinutes(1),
                LocalDateTime.now().plusMinutes(1));
        System.out.println(list.size());
    }
}
