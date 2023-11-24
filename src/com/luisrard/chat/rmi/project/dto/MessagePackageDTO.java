package com.luisrard.chat.rmi.project.dto;

import java.io.Serializable;

public class MessagePackageDTO implements Serializable {
    private final String message;
    private final String from;

    public MessagePackageDTO(String message, String from) {
        this.message = message;
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public String getFrom() {
        return from;
    }

    @Override
    public String toString() {
        return "MessagePackage{" +
                "message: " + message +
                ", from: " + from +
                '}';
    }
}
