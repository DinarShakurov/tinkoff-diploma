package ru.tfs.hw.spring_core.task1;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import ru.tfs.hw.spring_core.task1.config.AppConfig;

import java.text.DateFormat;
import java.util.Date;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class App {

    private final DateFormat localedDateFormat;
    private final DateFormat isoDateFormat;

    public static void main(String[] args) {
        ApplicationContext applicationContext = createApplicationContext("ru");
        App app = applicationContext.getBean(App.class);
        app.start();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        String command;
        while (!(command = scanner.nextLine()).equals("exit")) {
            DateFormat formatter = switch (command) {
                case "today" -> localedDateFormat;
                case "today-iso" -> isoDateFormat;
                default -> throw new UnsupportedOperationException("Unsupported command! Try 'today' or 'today-iso'.");
            };
            String response = formatter.format(new Date());
            System.out.println(response);
        }
    }

    private static ApplicationContext createApplicationContext(String... profiles) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.getEnvironment().setActiveProfiles(profiles);
        applicationContext.register(AppConfig.class);
        applicationContext.refresh();
        return applicationContext;
    }
}
