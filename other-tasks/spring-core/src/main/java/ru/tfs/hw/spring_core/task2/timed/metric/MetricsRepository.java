package ru.tfs.hw.spring_core.task2.timed.metric;

import java.util.List;
import java.util.Map;

interface MetricsRepository {

    void save(MetricUnit metricUnit);

    /**
     * key - method name
     * value - metrics for this method
     */
    Map<String, List<MetricUnit>> getAllMetrics();

    List<MetricUnit> getAllMetrics(String methodName);
}
