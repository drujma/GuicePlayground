package com.feps.facades;

import java.util.Map;

public interface IMetricsFacade {
    void publish(String namespace, String operation, String metric, String unit, double value);

    void publish(String namespace, String operation, String metric, Map<String, String> dimensions, String unit, double value);
}
