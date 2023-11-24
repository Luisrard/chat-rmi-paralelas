package com.luisrard.chat.rmi.project.dto;

import java.io.Serializable;

public class ConnectionDTO implements Serializable {
    private final String ip;
    private final String userName;

    public ConnectionDTO(String ip, String userName) {
        this.ip = ip;
        this.userName = userName;
    }

    public String getIp() {
        return ip;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return "Connection{ " +
                "ip: " + ip +
                ", userName: " + userName +
                " }";
    }
}
