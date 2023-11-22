package com.luisrard.chat.rmi.project.dto;

import java.time.LocalDateTime;

public class MessageDTO {
    private final String message;
    private final LocalDateTime dateTime;

    public MessageDTO(String message, LocalDateTime dateTime) {
        this.message = message;
        this.dateTime = dateTime;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }


    @Override
    public String toString() {
        return "Message{" +
                "message: " + message +
                ", dateTime: " + dateTime +
                '}';
    }
}