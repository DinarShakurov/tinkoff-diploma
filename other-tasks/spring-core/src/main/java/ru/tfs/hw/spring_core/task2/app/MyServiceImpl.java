package ru.tfs.hw.spring_core.task2.app;

import org.springframework.stereotype.Service;
import ru.tfs.hw.spring_core.task2.timed.Timed;

@Service
public class MyServiceImpl implements MyService {

    @Override
    @Timed
    public String doWork() throws InterruptedException {
        Thread.sleep(1000L);
        return "Hello, world!";
    }
}
