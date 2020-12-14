package com.feps.consumers;

import com.feps.handlers.IMessageProcessor;
import com.feps.annotations.MessageQueue;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.google.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class SqsConsumer implements Runnable {
    private final static int SLEEP_TIME_S = 1;

    private boolean stop = false;

    @Inject
    private AmazonSQS amazonSQSClient;

    @Inject
    IMessageProcessor messageHandler;

    private String queueName;

    @Inject
    public SqsConsumer(@MessageQueue String queueName) {
        this.queueName = queueName;
    }


    public synchronized void stop() {
        this.stop = true;
    }

    private synchronized boolean keepRunning() {
        return this.stop == false;
    }

    @Override
    public void run() {
        try {
            while (keepRunning()) {
                consumeMessages();
                Thread.sleep(TimeUnit.SECONDS.toMillis(SLEEP_TIME_S));
            }
        } catch (InterruptedException e) {
            System.out.println("Wow something interrupted me");
        }

        System.out.println("Something stopped the thread, I am exiting");
    }

    private void consumeMessages()  {
        final List<Message> messages = amazonSQSClient.receiveMessage(queueName).getMessages();
        System.out.println(String.format("Found %s message", messages.size()));
        for(Message message : messages) {
            messageHandler.processMessage(message.getBody(), Optional.ofNullable(message.getMD5OfBody()));

            System.out.println(String.format("Message handler %s", message.getReceiptHandle()));
            System.out.println(String.format("Message identifier %s", message.getMessageId()));
        }
    }
}
