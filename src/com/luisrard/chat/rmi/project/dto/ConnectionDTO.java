package com.luisrard.chat.rmi.project.dto;

import com.luisrard.chat.rmi.project.client.IRemoteClient;

import java.io.Serializable;

public class ConnectionDTO implements Serializable {
    private static final long serialVersionUID = 1;
    private final String userName;

    private IRemoteClient remoteClient;

    public ConnectionDTO(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setRemoteClient(IRemoteClient remoteClient) {
        this.remoteClient = remoteClient;
    }

    public IRemoteClient getRemoteClient() {
        return remoteClient;
    }

    @Override
    public String toString() {
        return "Connection{ "  + userName + " }";
    }
}
