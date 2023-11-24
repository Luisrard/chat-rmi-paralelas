package com.luisrard.chat.rmi.project.client;

import com.luisrard.chat.rmi.project.dto.ConnectionDTO;
import com.luisrard.chat.rmi.project.view.ChatView;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import static com.luisrard.chat.rmi.project.utils.Util.generateURLConnection;

public class Client {
    public static final String CONNECTION_CLIENT =  "/clientChat";

    public static void main(String[] args) {
        String ip = args[0];
        String userName = args[1];

        try {
            int port = Integer.parseInt(ip.split(":")[1]);
            LocateRegistry.createRegistry(port);

            ChatView chatView = new ChatView(new ConnectionDTO(ip, userName));

            IRemoteClient remoteClass = new RemoteClientImpl(
                    chatView::receiveMessage,
                    chatView::addUser
            );

            Naming.rebind(generateURLConnection(ip), remoteClass);

            System.out.println("Client " + userName + " running on " + ip);
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
