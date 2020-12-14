package com.feps.handlers;

import com.feps.messages.IMessage;

@FunctionalInterface
public interface IMessageHandler<Message extends IMessage> {
    void handleMessage(Message message);
}
