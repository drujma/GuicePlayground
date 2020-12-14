package com.feps.handlers;

import com.feps.facades.IMetricsFacade;
import com.google.inject.Inject;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class MetricInterceptor implements MethodInterceptor {
    @Inject
    IMetricsFacade metricsFacade;

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        System.out.println("Emitting metric");
        return methodInvocation.proceed();
    }
}
