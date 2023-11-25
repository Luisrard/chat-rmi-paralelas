package com.luisrard.chat.rmi.project.client;

import com.luisrard.chat.rmi.project.dto.ConnectionDTO;
import com.luisrard.chat.rmi.project.view.ChatView;

public class Client {
    public static final String CONNECTION_CLIENT =  "/clientChat";

    public static void main(String[] args) {
        String userName = args[0];

        try {
            ConnectionDTO localConnection = new ConnectionDTO(userName);
            ChatView chatView = new ChatView(localConnection);
            IRemoteClient remoteClass = new RemoteClientImpl(
                    chatView::receiveMessage,
                    chatView::addUser
            );
            localConnection.setRemoteClient(remoteClass);
            chatView.connectToServer();
            System.out.println("Client " + userName + " running");
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
