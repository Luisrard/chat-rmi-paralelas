package com.luisrard.chat.rmi.project.dto;

public class MessagePackageDTO {
    private final MessageDTO message;
    private final ConnectionDTO from;
    private final ConnectionDTO to;

    public MessagePackageDTO(MessageDTO message, ConnectionDTO from, ConnectionDTO to) {
        this.message = message;
        this.from = from;
        this.to = to;
    }

    public MessageDTO getMessage() {
        return message;
    }

    public ConnectionDTO getFrom() {
        return from;
    }

    public ConnectionDTO getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "MessagePackage{" +
                "message: " + message +
                ", from: " + from +
                ", to: " + to +
                '}';
    }
}
