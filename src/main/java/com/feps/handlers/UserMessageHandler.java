package com.feps.handlers;

import com.feps.messages.UserMessage;

public class UserMessageHandler implements IMessageHandler<UserMessage> {

    @Override
    @HandleMessage(UserMessage.class)
    public void handleMessage(UserMessage message) {
        System.out.println("Wow It worked");
    }
}
