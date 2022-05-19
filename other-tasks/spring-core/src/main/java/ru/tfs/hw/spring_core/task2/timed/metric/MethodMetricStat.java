package ru.tfs.hw.spring_core.task2.timed.metric;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MethodMetricStat {
    /**
     * Наименование/идентификатор метода
     */
    private String methodName;

    /**
     * Кол-во вызовов метода
     */
    private Integer invocationsCount;

    /**
     * Минимальное время работы метода
     */
    private long minTime;

    /**
     * Среднее время работы метода
     */
    private long averageTime;

    /**
     * максимальное время работы метода
     */
    private long maxTime;
}
