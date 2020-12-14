package com.feps.exceptions;

public class MessageNotValidException extends RuntimeException{

    public MessageNotValidException(String message) {
        super(message);
    }

    public MessageNotValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageNotValidException(Throwable cause) {
        super(cause);
    }
}
