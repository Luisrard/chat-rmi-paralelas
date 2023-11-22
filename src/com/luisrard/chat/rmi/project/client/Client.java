package com.luisrard.chat.rmi.project.client;

import com.luisrard.chat.rmi.project.dto.ConnectionDTO;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    public static final int CLIENT_PORT = 9020;
    public static final String CONNECTION_CLIENT = ":" + CLIENT_PORT  + "/clientChat";

    public static void main(String[] args) {
        String ip = args[0];
        String userName = args[1];

        try {

            Registry registry = LocateRegistry.createRegistry(CLIENT_PORT);

            IRemoteClient remoteClass = new RemoteClientImpl();

            new ClientChat(new ConnectionDTO(ip, userName)).run();

            Naming.rebind("//" + ip +  CONNECTION_CLIENT,
                    remoteClass);

            System.out.println("Client running on port: " + CLIENT_PORT);
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
