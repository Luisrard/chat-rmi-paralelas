package com.luisrard.chat.rmi.project.client;

import com.luisrard.chat.rmi.project.dto.ConnectionDTO;
import com.luisrard.chat.rmi.project.dto.MessagePackageDTO;
import com.luisrard.chat.rmi.project.utils.IFunction;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteClientImpl  extends UnicastRemoteObject implements IRemoteClient {
    private final IFunction<MessagePackageDTO> onMessageReceived;
    private final IFunction<ConnectionDTO> onNewConnectionReceived;
    protected RemoteClientImpl(IFunction<MessagePackageDTO> onMessageReceived,
                               IFunction<ConnectionDTO> onNewConnectionReceived)
            throws RemoteException {
        this.onMessageReceived = onMessageReceived;
        this.onNewConnectionReceived = onNewConnectionReceived;
    }

    @Override
    public void receiveMessage(MessagePackageDTO messagePackageDTO) throws RemoteException {
        onMessageReceived.apply(messagePackageDTO);
    }

    @Override
    public void receiveNewConnection(ConnectionDTO connectionDTO) throws RemoteException {
        onNewConnectionReceived.apply(connectionDTO);
    }
}
