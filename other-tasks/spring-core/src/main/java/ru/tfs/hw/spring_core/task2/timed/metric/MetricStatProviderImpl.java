package ru.tfs.hw.spring_core.task2.timed.metric;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
class MetricStatProviderImpl implements MetricStatProvider {
    private final MetricsRepository metricsRepository;

    @Override
    public List<MethodMetricStat> getTotalStatForPeriod(LocalDateTime from, LocalDateTime to) {
        Predicate<MetricUnit> datePredicate = metricUnit -> between(from, to, metricUnit.getCreateTime());

        return metricsRepository.getAllMetrics()
                .values()
                .stream()
                .map(metricUnits -> filterAndParse(metricUnits, datePredicate))
                .toList();
    }

    @Override
    public MethodMetricStat getTotalStatByMethodForPeriod(String method, LocalDateTime from, LocalDateTime to) {
        return filterAndParse(
                metricsRepository.getAllMetrics(method),
                metricUnit -> between(from, to, metricUnit.getCreateTime())
        );
    }

    private boolean between(LocalDateTime left, LocalDateTime right, LocalDateTime val) {
        return !(val.isBefore(left) || val.isAfter(right));
    }

    private MethodMetricStat filterAndParse(List<MetricUnit> metrics, Predicate<MetricUnit> predicate) {
        LongSummaryStatistics lss = metrics.stream()
                .filter(predicate)
                .mapToLong(MetricUnit::getExecutionTime)
                .summaryStatistics();
        MethodMetricStat methodMetricStat = new MethodMetricStat();
        methodMetricStat.setMethodName(metrics.get(0).getMethodName());
        methodMetricStat.setAverageTime((long) lss.getAverage());
        methodMetricStat.setMaxTime(lss.getMax());
        methodMetricStat.setMinTime(lss.getMin());
        methodMetricStat.setInvocationsCount(metrics.size());
        return methodMetricStat;
    }


}
