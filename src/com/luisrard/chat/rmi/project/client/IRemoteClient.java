package com.luisrard.chat.rmi.project.client;

import com.luisrard.chat.rmi.project.dto.ConnectionDTO;
import com.luisrard.chat.rmi.project.dto.MessagePackageDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRemoteClient extends Remote {
    void receiveMessage(MessagePackageDTO message) throws RemoteException;
    void receiveNewConnection(ConnectionDTO connectionDTO) throws RemoteException;
}
