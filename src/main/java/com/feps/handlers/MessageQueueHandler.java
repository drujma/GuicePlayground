package com.feps.handlers;

import com.google.inject.Inject;
import com.feps.consumers.SqsConsumer;

public class MessageQueueHandler {

    private Thread thread;
    private SqsConsumer sqsConsumer;

    @Inject
    public MessageQueueHandler(SqsConsumer sqsConsumer) {
        this.sqsConsumer = sqsConsumer;
        this.thread = new Thread(sqsConsumer);
    }

    public void start() {
        thread.start();
    }

    public void stop() {
        sqsConsumer.stop();
    }
}
