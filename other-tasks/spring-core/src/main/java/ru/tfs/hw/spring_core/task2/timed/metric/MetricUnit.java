package ru.tfs.hw.spring_core.task2.timed.metric;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
class MetricUnit {

    /**
     * Имя метода
     */
    private final String methodName;

    /**
     * Дата создания
     */
    private final LocalDateTime createTime;

    /**
     * Время выполнение метода
     */
    private final long executionTime;
}
