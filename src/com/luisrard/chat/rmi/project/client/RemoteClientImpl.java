package com.luisrard.chat.rmi.project.client;

import com.luisrard.chat.rmi.project.model.dto.ConnectionDTO;
import com.luisrard.chat.rmi.project.model.dto.MessagePackageDTO;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.function.Consumer;

public class RemoteClientImpl  extends UnicastRemoteObject implements IRemoteClient {
    private final Consumer<MessagePackageDTO> onMessageReceived;
    private final Consumer<ConnectionDTO> onNewConnectionReceived;
    protected RemoteClientImpl(Consumer<MessagePackageDTO> onMessageReceived,
                               Consumer<ConnectionDTO> onNewConnectionReceived)
            throws RemoteException {
        this.onMessageReceived = onMessageReceived;
        this.onNewConnectionReceived = onNewConnectionReceived;
    }

    @Override
    public void receiveMessage(MessagePackageDTO messagePackageDTO) throws RemoteException {
        onMessageReceived.accept(messagePackageDTO);
    }

    @Override
    public void receiveConnection(ConnectionDTO connectionDTO) throws RemoteException {
        onNewConnectionReceived.accept(connectionDTO);
    }
}
