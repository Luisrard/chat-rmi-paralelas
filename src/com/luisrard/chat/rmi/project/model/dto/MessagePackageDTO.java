package com.luisrard.chat.rmi.project.model.dto;

import java.io.Serializable;

public class MessagePackageDTO implements Serializable {
    private static final long serialVersionUID = 2;
    private final String from;
    private final String message;

    private final boolean broadcast;

    public MessagePackageDTO(String from, String message, boolean broadcast) {
        this.from = from;
        this.message = message;
        this.broadcast = broadcast;
    }

    public String getMessage() {
        return message;
    }

    public String getFrom() {
        return from;
    }

    public boolean isBroadcast() {
        return broadcast;
    }

    @Override
    public String toString() {
        return "MessagePackage{" +
                "message: " + message +
                ", from: " + from +
                ", broadcast: " + broadcast +
                '}';
    }
}
