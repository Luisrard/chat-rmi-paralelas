package com.luisrard.chat.rmi.project.server;

import com.luisrard.chat.rmi.project.model.dto.ConnectionDTO;
import com.luisrard.chat.rmi.project.model.dto.MessagePackageDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

public interface IRemoteServer extends Remote {
    Set<ConnectionDTO> connectToServer(ConnectionDTO connectionDTO) throws RemoteException;

    void broadcastMessage(MessagePackageDTO messagePackageDTO) throws RemoteException;
}
