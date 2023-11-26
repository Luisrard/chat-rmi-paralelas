package com.luisrard.chat.rmi.project.server;

import com.luisrard.chat.rmi.project.client.IRemoteClient;
import com.luisrard.chat.rmi.project.model.dto.ConnectionDTO;
import com.luisrard.chat.rmi.project.model.dto.MessagePackageDTO;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RemoteServerImpl extends UnicastRemoteObject implements IRemoteServer {
    private final HashSet<ConnectionDTO> connections;

    protected RemoteServerImpl() throws RemoteException {
        connections = new HashSet<>();
    }

    @Override
    public Set<ConnectionDTO> connectToServer(final ConnectionDTO connectionDTO) throws RemoteException {
        connections.remove(connectionDTO);
        connections.add(connectionDTO);

        System.out.println("Adding " + connectionDTO);
        ThreadPoolExecutor executor =
                (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        for(final ConnectionDTO connection : connections){
            if(!Objects.equals(connection, connectionDTO)){
                executor.execute(() -> {
                    try{
                        IRemoteClient client = connection.getRemoteClient();
                        System.out.println("Sending " + connectionDTO + ", to: " + connection);
                        client.receiveConnection(connectionDTO);
                        System.out.println(connectionDTO + " sent to: " + connection);
                    } catch (Exception e){
                        System.out.println("Error sending" + connectionDTO + ", to: " + connection);
                        e.printStackTrace();
                    }
                });
            }
        }
        try {
            executor.shutdown();
            executor.awaitTermination(120, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println("Error stopping executor");
        }

        System.out.println(connectionDTO+ " added");
        return connections;
    }

    @Override
    public void broadcastMessage(MessagePackageDTO messagePackageDTO) throws RemoteException {
        System.out.println("Broadcasting " + messagePackageDTO);

        ThreadPoolExecutor executor =
                (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        for(final ConnectionDTO connection : connections){
            if(!Objects.equals(connection.getUserName(), messagePackageDTO.getFrom())){
                executor.execute(() -> {
                    try{
                        IRemoteClient client = connection.getRemoteClient();
                        System.out.println("Sending " + messagePackageDTO + ", to: " + connection);
                        client.receiveMessage(messagePackageDTO);
                        System.out.println(messagePackageDTO + " sent to: " + connection);
                    } catch (Exception e){
                        System.out.println("Error sending" + messagePackageDTO + ", to: " + connection);
                        e.printStackTrace();
                    }
                });
            }
        }
        System.out.println(messagePackageDTO + " sent to all users");
    }

}
