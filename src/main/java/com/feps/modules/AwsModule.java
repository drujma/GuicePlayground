package com.feps.modules;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.feps.annotations.MessageQueue;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.feps.facades.CloudWatchFacade;
import com.feps.facades.IMetricsFacade;
import com.feps.handlers.Count;
import com.feps.handlers.MetricInterceptor;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.matcher.Matchers;

import static com.google.inject.matcher.Matchers.annotatedWith;

public class AwsModule extends AbstractModule {

    @Override
    protected void configure() {
        bindConstant()
                .annotatedWith(MessageQueue.class)
                .to(getSqsQueueName());
    }

    @Provides
    public AmazonSQS getSqsClient() {
        return AmazonSQSClientBuilder.defaultClient();
    }

    @Provides
    public AmazonCloudWatch getCloudWatchClient() {
        return AmazonCloudWatchClientBuilder.defaultClient();
    }


    private String getSqsQueueName() {
        return "https://sqs.eu-west-1.amazonaws.com/770793544302/EventQueue.fifo";
    }
}
