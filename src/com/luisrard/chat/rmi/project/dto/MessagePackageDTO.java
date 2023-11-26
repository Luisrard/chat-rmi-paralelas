package com.luisrard.chat.rmi.project.dto;

import java.io.Serializable;

public class MessagePackageDTO implements Serializable {
    private static final long serialVersionUID = 1;
    private final String from;
    private final String message;

    public MessagePackageDTO(String from, String message) {
        this.from = from;
        this.message = message;
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
