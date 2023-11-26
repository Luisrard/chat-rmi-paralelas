package com.luisrard.chat.rmi.project.client;

import com.luisrard.chat.rmi.project.model.dto.ConnectionDTO;
import com.luisrard.chat.rmi.project.model.dto.MessagePackageDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRemoteClient extends Remote {
    void receiveMessage(MessagePackageDTO message) throws RemoteException;
    void receiveConnection(ConnectionDTO connectionDTO) throws RemoteException;
}
