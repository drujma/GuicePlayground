package com.feps.handlers;

import java.util.Optional;

public interface IMessageProcessor {
    void processMessage(String message, Optional<String> checksum);
}
