package com.feps.facades;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.MetricDatum;
import com.amazonaws.services.cloudwatch.model.PutMetricDataRequest;
import com.amazonaws.services.cloudwatch.model.StandardUnit;
import com.google.inject.Inject;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CloudWatchFacade implements IMetricsFacade {
    private AmazonCloudWatch cloudWatch;

    @Inject
    public CloudWatchFacade(AmazonCloudWatch cloudWatch) {
        this.cloudWatch = cloudWatch;
    }

    @Override
    public void publish(String namespace, String operation, String metric, String unit, double value) {
        publish(namespace, operation, metric, Collections.emptyMap(), unit, value);
    }

    @Override
    public void publish(String namespace, String operation, String metric, Map<String, String> dimensions, String unit, double value) {
        dimensions.put("operation", operation);
        publish(namespace, metric, dimensions, unit, value);
    }

    private void publish(String namespace, String metric, Map<String, String> dimensions, String unit, double value){
        final MetricDatum metricDatum = new MetricDatum()
                .withMetricName(metric)
                .withUnit(unit)
                .withValue(value)
                .withDimensions(buildDimensionObject(dimensions));

        final PutMetricDataRequest request = new PutMetricDataRequest()
                .withNamespace(namespace)
                .withMetricData(metricDatum);
    }

    private List<Dimension> buildDimensionObject(Map<String, String> dimensions) {
        return dimensions.entrySet().stream().map(stringStringEntry -> new Dimension()
                .withName(stringStringEntry.getKey())
                .withValue(stringStringEntry.getValue())).collect(Collectors.toList());

    }
}
