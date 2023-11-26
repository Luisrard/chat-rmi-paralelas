package com.luisrard.chat.rmi.project.view;

import com.luisrard.chat.rmi.project.client.IRemoteClient;
import com.luisrard.chat.rmi.project.model.ConnectionStatus;
import com.luisrard.chat.rmi.project.model.dto.ConnectionDTO;
import com.luisrard.chat.rmi.project.model.dto.MessagePackageDTO;
import com.luisrard.chat.rmi.project.server.IRemoteServer;
import com.luisrard.chat.rmi.project.server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.*;
import java.util.List;

public class ChatView extends JFrame {
    private static final String BROADCAST_CONVERSATION = "broadcast";
    private final ConnectionDTO localConnection;
    private final HashMap<String, List<String>> conversations;
    private final HashMap<String, ConnectionDTO> connections;
    private final HashMap<String, JButton> buttons;

    private IRemoteServer remoteServer;
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
        buttons = new HashMap<>();

        componentsHolder.setBounds(20, 0, 81, 140);
        componentsHolder.setLayout(new BoxLayout(componentsHolder, BoxLayout.Y_AXIS));

        messageField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER && sendButton.isEnabled()){
                    sendMessage();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        sendButton.addActionListener(e -> sendMessage());

        addUser(new ConnectionDTO(BROADCAST_CONVERSATION));
    }

    private void initGUI(){
        setContentPane(mainPanel);
        setTitle("FACENOTEBOOK");

        setSize(1000, 800);
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Disconnecting");
                localConnection.setConnected(false);
                try {
                    remoteServer.connectToServer(localConnection);
                } catch (RemoteException ex) {
                    System.out.println("Error disconnecting");
                    ex.printStackTrace();
                }
                System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });

        userLabel.setText("User: " + localConnection.getUserName());

        setVisible(true);
    }

    public void connectToServer(){
        new Thread(() -> {
            try {
                remoteServer = (IRemoteServer) Naming.lookup(Server.CONNECTION_NAME);
                Set<ConnectionDTO> connectionDTOS = remoteServer.connectToServer(localConnection);
                connectionDTOS.forEach(this::addUser);
                logInfo("Successfully connected to server");
            } catch (Exception e) {
                System.out.println("Error connecting to server, please retry later");
                e.printStackTrace();
                System.exit(400);
            }
        }).start();
    }


    private void loadUserFriendChat(String name, JButton button){
        if(conversationActiveButton != null){
            conversationActiveButton.setEnabled(true);
        }
        button.setEnabled(false);
        conversationActiveButton = button;

        ConnectionDTO userConnection = connections.get(name);
        if(userConnection.isConnected()){
            sendButton.setEnabled(true);
            changeConnectionStatus(ConnectionStatus.ONLINE, button);
        }else{
            sendButton.setEnabled(false);
            changeConnectionStatus(ConnectionStatus.DISCONNECTED, button);
        }

        List<String> messages= conversations.get(name);
        String conversation = String.join("", messages);

        chatText.setText(conversation);
    }

    private void addTextMessage(String userConversation, String user, String message){
        String messageFormatted = formatMessage(user, message);
        conversations.get(userConversation).add(messageFormatted);
        if(conversationActiveButton != null && Objects.equals(conversationActiveButton.getText(), userConversation)){
            chatText.append(messageFormatted);
        }else{
            changeConnectionStatus(ConnectionStatus.NEW_MESSAGE, buttons.get(userConversation));
        }
        System.out.println("adding message in gui");
    }

    private void sendMessage(){
        String message = messageField.getText();
        if(!message.isEmpty()) {
            //clear messageField
            messageField.setText("");
            String userConversation = conversationActiveButton.getText();
            addTextMessage(userConversation, "me", message);

            if (Objects.equals(userConversation, BROADCAST_CONVERSATION)) {
                sendBroadcastMessage(message);
            } else {
                sendMessageToUser(userConversation, message);
            }
        }
    }

    private void sendMessageToUser(String userConversation, String message){
        try {
            IRemoteClient remote = connections.get(userConversation).getRemoteClient();
            remote.receiveMessage(new MessagePackageDTO(localConnection.getUserName(), message, false));
            logInfo("Message sent to " + userConversation);
        } catch (Exception e) {
            logInfo("Error sending message to " + userConversation);
            e.printStackTrace();
        }
    }

    private void sendBroadcastMessage(String message){
        try {
            System.out.println("Sending broadcast: " + message);
            remoteServer.broadcastMessage(new MessagePackageDTO(localConnection.getUserName(), message, true));
            logInfo("Broadcast sent");
        } catch (Exception e) {
            logInfo("Error sending broadcast " + message);
            e.printStackTrace();
        }
    }
    public void receiveMessage(MessagePackageDTO messagePackageDTO){
        System.out.println("Receiving message " + messagePackageDTO);
        String from = messagePackageDTO.getFrom();

        addTextMessage(messagePackageDTO.isBroadcast() ? BROADCAST_CONVERSATION : from, from, messagePackageDTO.getMessage());
        System.out.println("message added in conversation");
    }

    public void addUser(ConnectionDTO connectionDTO){
        if(!Objects.equals(connectionDTO.getUserName(), localConnection.getUserName())) {
            String name = connectionDTO.getUserName();
            if (connectionDTO.isConnected()) {
                JButton newUserButton = new JButton(name);
                changeConnectionStatus(ConnectionStatus.ONLINE, newUserButton);
                newUserButton.addActionListener(e -> loadUserFriendChat(name, newUserButton));
                componentsHolder.add(newUserButton);

                conversations.put(name, new ArrayList<>());
                buttons.put(name, newUserButton);
                connections.put(name, connectionDTO);
            }
            else{
                JButton userButton = buttons.get(name);
                changeConnectionStatus(ConnectionStatus.DISCONNECTED, userButton);
            }
        }
    }

    private void changeConnectionStatus(ConnectionStatus connectionStatus, JButton button){
        switch (connectionStatus){
            case ONLINE:
                button.setBackground(Color.LIGHT_GRAY);
                break;
            case NEW_MESSAGE:
                button.setBackground(Color.RED);
                break;
            case DISCONNECTED:
                button.setBackground(Color.DARK_GRAY);
                break;
        }
    }

    private String formatMessage(String user, String message){
        return "\n" + user + ": " + message;
    }

    private void logInfo(String logMessage){
        logLabel.setText(logMessage);
    }
}
