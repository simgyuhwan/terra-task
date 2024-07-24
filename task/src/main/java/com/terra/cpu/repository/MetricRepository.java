package com.terra.cpu.repository;

import com.terra.cpu.controller.response.CpuMetricResponse;
import com.terra.cpu.controller.response.MemMetricResponse;
import com.terra.cpu.domain.Metric;
import com.terra.cpu.domain.TimeRange;
import java.util.List;

public interface MetricRepository {

  void saveMetric(Metric metric);

  List<CpuMetricResponse> queryCpuMetric(String start, String stop);

  List<MemMetricResponse> queryMemMetric(String start, String stop);

  List<CpuMetricResponse> queryCpuMetric(TimeRange type);

  List<MemMetricResponse> queryMemMetric(TimeRange type);
}
