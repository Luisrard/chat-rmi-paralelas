package com.luisrard.chat.rmi.project.server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static final int SERVER_PORT = 9010;
    public static final String CONNECTION_NAME = "//192.168.0.101:" + SERVER_PORT + "/chatServer";

    public static void main(String[] args) {
        try {

            Registry registry = LocateRegistry.createRegistry(SERVER_PORT);

            IRemoteServer remoteClass = new RemoteServerImpl();

            Naming.rebind(CONNECTION_NAME,
                    remoteClass);

            System.out.println("Server running on port: " + SERVER_PORT);
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
