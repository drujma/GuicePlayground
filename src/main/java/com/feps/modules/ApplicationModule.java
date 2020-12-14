package com.feps.modules;

import com.feps.facades.CloudWatchFacade;
import com.feps.facades.IMetricsFacade;
import com.feps.factories.MessageHandlerFactory;
import com.feps.handlers.*;
import com.feps.messages.IMessage;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.matcher.Matchers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Map;

public class ApplicationModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IMessageProcessor.class).to(MessageProcessor.class);
        bind(IMetricsFacade.class).to(CloudWatchFacade.class);

        final MetricInterceptor metricInterceptor = new MetricInterceptor();
        requestInjection(metricInterceptor);
        bindInterceptor(Matchers.any(),
                Matchers.annotatedWith(Count.class),
                metricInterceptor);

    }

    @Provides
    public MessageDigest getMessageDigest() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Provides
    public Map<Class<? extends IMessage>, IMessageHandler<IMessage>> getMessageHandler() {
        try{
            MessageHandlerFactory factory = new MessageHandlerFactory();
            return factory.getMessageHandlers();
        } catch (Exception e){
            return Collections.emptyMap();
        }
    }
}
