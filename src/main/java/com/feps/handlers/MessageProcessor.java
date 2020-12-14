package com.feps.handlers;

import com.feps.exceptions.MessageNotValidException;
import com.feps.messages.UserMessage;
import com.google.inject.Inject;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.util.Optional;

public class MessageProcessor implements IMessageProcessor {
    private Optional<MessageDigest> messageDigest;

    @Inject
    private MessageConverter messageConverter;

    @Inject
    private MessageDispatcher messageDispatcher;

    @Inject
    public MessageProcessor(MessageDigest messageDigest) {
        this.messageDigest = Optional.ofNullable(messageDigest);
    }

    @Override
    public void processMessage(String message, Optional<String> checksum) {
        checksum.ifPresent(md5 -> validateMessage(message, md5));
        System.out.printf("Message: '%s' is valid\n", message);
        messageConverter.convertMessage(message).ifPresent(messageDispatcher::handle);
    }

    private void validateMessage(String message, String checksum) {
        final String messageComputedChecksum =
                computeChecksum(message);

        if (!messageComputedChecksum.equalsIgnoreCase(checksum)) {
            throw new MessageNotValidException("Message Checksum does not match");
        }
    }

    private String computeChecksum(String message) {
        final MessageDigest messageDigest = this.messageDigest
                .orElseThrow(() -> new RuntimeException("I fucked up"));

        messageDigest.reset();
        messageDigest.update(message.getBytes());
        return DatatypeConverter.printHexBinary(messageDigest.digest());
    }
}
