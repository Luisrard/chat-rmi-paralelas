package com.luisrard.chat.rmi.project.view;

import com.luisrard.chat.rmi.project.client.IRemoteClient;
import com.luisrard.chat.rmi.project.dto.ConnectionDTO;
import com.luisrard.chat.rmi.project.dto.MessagePackageDTO;
import com.luisrard.chat.rmi.project.server.IRemoteServer;
import com.luisrard.chat.rmi.project.server.Server;

import javax.swing.*;
import java.rmi.Naming;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ChatView extends JFrame {
    private final ConnectionDTO localConnection;
    private final HashMap<String, List<String>> conversations;
    private final HashMap<String, ConnectionDTO> connections;
    private JButton conversationActiveButton;
    private JPanel mainPanel;
    private JPanel header;
    private JPanel body;
    private JPanel center;
    private JPanel logPanel;
    private JLabel logLabel;
    private JPanel createdThreads;
    private JLabel userLabel;
    private JTextArea chatText;
    private JButton sendButton;
    private JTextField messageField;
    private JPanel componentsHolder;
    private JPanel asd;
    private JButton startButton;
    private JButton stopButton;

    public ChatView(ConnectionDTO localConnection){
        this.localConnection = localConnection;
        initGUI();

        conversations = new HashMap<>();
        connections = new HashMap<>();

        componentsHolder.setBounds(20, 0, 81, 140);
        componentsHolder.setLayout(new BoxLayout(componentsHolder, BoxLayout.Y_AXIS));

        sendButton.addActionListener(e -> sendMessage());

    }

    private void initGUI(){
        setContentPane(mainPanel);
        setTitle("FACENOTEBOOK");

        setSize(1000, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        userLabel.setText("User: " + localConnection.getUserName());

        setVisible(true);
    }

    public void connectToServer(){
        new Thread(() -> {
            try {
                IRemoteServer remote = (IRemoteServer) Naming.lookup(Server.CONNECTION_NAME);
                List<ConnectionDTO> connectionDTOS = remote.connectToServer(localConnection);
                connectionDTOS.forEach(this::addUser);
                logInfo("Successfully connected to server");
            } catch (Exception e) {
                System.out.println(e);
                logInfo("Error connecting to server, please restart");
            }
        }).start();
    }

    public void addUser(ConnectionDTO connectionDTO){
        if(!Objects.equals(connectionDTO.getUserName(), localConnection.getUserName())) {
            String name = connectionDTO.getUserName();
            JButton newUserButton = new JButton(name);
            newUserButton.addActionListener(e -> loadUserFriendChat(name, newUserButton));
            componentsHolder.add(newUserButton);
            conversations.put(name, new ArrayList<>());
            connections.put(name, connectionDTO);
        }
    }

    private void loadUserFriendChat(String name, JButton button){
        sendButton.setEnabled(true);
        if(conversationActiveButton != null){
            conversationActiveButton.setEnabled(true);
        }
        button.setEnabled(false);
        conversationActiveButton = button;

        List<String> messages= conversations.get(name);
        String conversation = String.join("", messages);

        chatText.setText(conversation);
    }

    private void addText(String userConversation, String user, String message){
        String messageFormatted = "\n" + user + ": " + message;
        conversations.get(userConversation).add(messageFormatted);
        chatText.append(messageFormatted);
        System.out.println("adding message in gui");
    }

    private void sendMessage(){
        String message = messageField.getText();

        messageField.setText("");
        String userConversation = conversationActiveButton.getText();
        addText(userConversation,"Me", message);

        sendMessageToUser(userConversation, message);
    }

    private void sendMessageToUser(String userConversation, String message){
        try {
            IRemoteClient remote = connections.get(userConversation).getRemoteClient();
            remote.receiveMessage(new MessagePackageDTO(localConnection.getUserName(), message));
            logInfo("Message sent to " + userConversation);
        } catch (Exception e) {
            System.out.println(e);
            logInfo("Error sending message to " + userConversation);
        }
    }

    public void receiveMessage(MessagePackageDTO messagePackageDTO){
        System.out.println("Receiving message " + messagePackageDTO);
        String from = messagePackageDTO.getFrom();
        if(conversationActiveButton != null && Objects.equals(conversationActiveButton.getText(), from)){
            addText(from, from, messagePackageDTO.getMessage());
        }

        conversations.get(from)
                .add(messagePackageDTO.getMessage());
        System.out.println("message added in conversation");
    }

    private void logInfo(String logMessage){
        logLabel.setText(logMessage);
    }
}
