package com.feps.handlers;

import com.feps.factories.MessageHandlerFactory;
import com.feps.messages.IMessage;
import com.google.inject.Inject;

import java.util.Map;

public class MessageDispatcher {
    @Inject
    Map<Class<? extends IMessage>, IMessageHandler<IMessage>> handlers;

    @Count
    public void handle(IMessage message) {
        final IMessageHandler<IMessage> iMessageIMessageHandler = handlers.get(message.getClass());
        iMessageIMessageHandler.handleMessage(message);
    }
}
