package com.feps.handlers;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.feps.messages.IMessage;

import java.io.IOException;
import java.util.Optional;

public class MessageConverter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Optional<IMessage> convertMessage(final String message){
        try {
            return Optional.ofNullable(objectMapper.readValue(message, IMessage.class));
        } catch (IOException e) {
            System.out.println("Failed to parse message");
        }

        return Optional.empty();
    }
}
