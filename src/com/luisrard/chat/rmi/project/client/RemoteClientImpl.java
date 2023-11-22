package com.luisrard.chat.rmi.project.client;

import com.luisrard.chat.rmi.project.dto.ConnectionDTO;
import com.luisrard.chat.rmi.project.dto.MessageDTO;
import com.luisrard.chat.rmi.project.dto.MessagePackageDTO;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class RemoteClientImpl  extends UnicastRemoteObject implements IRemoteClient {
    private final HashMap<String, List<MessageDTO>> messages;
    private final HashSet<ConnectionDTO> connections;


    protected RemoteClientImpl() throws RemoteException {
        messages = new HashMap<>();
        connections = new HashSet<>();
    }

    @Override
    public void receiveMessage(MessagePackageDTO messageDTO) throws RemoteException {
        System.out.println("Message " + messageDTO);
        ConnectionDTO from = messageDTO.getFrom();
        messages.compute(from.getUserName(), (name, messages) -> {
            if(messages == null){
                messages = new ArrayList<>();
            }
            else{
                messages.add(messageDTO.getMessage());
            }
            return messages;
        });
    }

    @Override
    public void receiveNewConnection(ConnectionDTO connectionDTO) throws RemoteException {
        System.out.println("User " + connectionDTO + " is online");
        connections.add(connectionDTO);
    }
}
