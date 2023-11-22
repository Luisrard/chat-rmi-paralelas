package com.luisrard.chat.rmi.project.server;

import com.luisrard.chat.rmi.project.dto.ConnectionDTO;
import com.luisrard.chat.rmi.project.dto.MessagePackageDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IRemoteServer extends Remote {
    List<ConnectionDTO> connectToServer(ConnectionDTO connectionDTO) throws RemoteException;
    String sendMessage(MessagePackageDTO message) throws RemoteException;
}
