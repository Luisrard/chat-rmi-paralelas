package com.luisrard.chat.rmi.project.client;

import com.luisrard.chat.rmi.project.dto.ConnectionDTO;
import com.luisrard.chat.rmi.project.server.IRemoteServer;

import java.rmi.Naming;

import static com.luisrard.chat.rmi.project.server.Server.CONNECTION_NAME;

public class ClientChat implements Runnable{
    private final ConnectionDTO client;

    public ClientChat(ConnectionDTO client) {
        this.client = client;
    }

    @Override
    public void run() {
        System.out.println("Ando corriendo");
        try {
            IRemoteServer remote = (IRemoteServer) Naming.lookup(CONNECTION_NAME);
            remote.connectToServer(client);
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Ya no corro");
    }
}
