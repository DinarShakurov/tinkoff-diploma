package ru.tfs.hw.spring_core.task2.timed.metric;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
class MetricsRepositoryImpl implements MetricsRepository {

    private final Map<String, List<MetricUnit>> map = new HashMap<>();

    @Override
    public void save(MetricUnit metricUnit) {
        synchronized (map) {
            List<MetricUnit> metrics = map.getOrDefault(metricUnit.getMethodName(), new ArrayList<>());
            map.put(metricUnit.getMethodName(), metrics);
            metrics.add(metricUnit);
        }
    }

    @Override
    public Map<String, List<MetricUnit>> getAllMetrics() {
        return new HashMap<>(map);
    }

    @Override
    public List<MetricUnit> getAllMetrics(String methodName) {
        return List.copyOf(
                map.getOrDefault(methodName, new ArrayList<>())
        );
    }
}
