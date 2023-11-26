package com.luisrard.chat.rmi.project.model.dto;

import com.luisrard.chat.rmi.project.client.IRemoteClient;

import java.io.Serializable;
import java.util.Objects;

public class ConnectionDTO implements Serializable {
    private static final long serialVersionUID = 2;
    private final String userName;

    private IRemoteClient remoteClient;

    private boolean connected;

    public ConnectionDTO(String userName) {
        this.userName = userName;
        this.connected = true;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
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
        return "Connection{" +
                "user: " + userName +
                ", connected: " + connected +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof ConnectionDTO))
            return false;

        ConnectionDTO connection = (ConnectionDTO) obj;
        return Objects.equals(userName, connection.userName);
    }
}
