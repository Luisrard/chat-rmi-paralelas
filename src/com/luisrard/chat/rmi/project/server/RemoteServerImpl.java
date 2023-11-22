package com.luisrard.chat.rmi.project.server;

import com.luisrard.chat.rmi.project.client.IRemoteClient;
import com.luisrard.chat.rmi.project.dto.ConnectionDTO;
import com.luisrard.chat.rmi.project.dto.MessagePackageDTO;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.luisrard.chat.rmi.project.utils.Util.generateURLConnection;

public class RemoteServerImpl extends UnicastRemoteObject implements IRemoteServer {
    private final List<ConnectionDTO> connections;

    protected RemoteServerImpl() throws RemoteException {
        connections = new ArrayList<>();
    }

    @Override
    public List<ConnectionDTO> connectToServer(final ConnectionDTO connectionDTO) throws RemoteException {
        System.out.println("Adding " + connectionDTO);
        ThreadPoolExecutor executor =
                (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        for(final ConnectionDTO connection : connections){
            executor.execute(() -> {
                try{
                    IRemoteClient client = (IRemoteClient) Naming.lookup(generateURLConnection(connection.getIp()));
                    System.out.println("Enviando " + connectionDTO + ", para: " + connection);
                    client.receiveNewConnection(connectionDTO);
                    System.out.println("Conexion " + connectionDTO + " enviado para: " + connection);
                } catch (Exception e){
                    System.out.println(e);
                }
            });
        }
        try {
            executor.awaitTermination(120, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println(e);
        }

        connections.add(connectionDTO);
        return connections;
    }

    @Override
    public String sendMessage(MessagePackageDTO message) throws RemoteException {
        try{
            IRemoteClient client = (IRemoteClient) Naming.lookup(generateURLConnection(message.getTo().getIp()));
            System.out.println("Enviando " + message);
            client.receiveMessage(message);
            System.out.println("Mensaje enviado " + message);
            return "Message Sent";
        } catch (Exception e){
            System.out.println(e);
        }
        return "Error sending message";
    }
}
