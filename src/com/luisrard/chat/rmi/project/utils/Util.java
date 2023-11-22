package com.luisrard.chat.rmi.project.utils;

import static com.luisrard.chat.rmi.project.client.Client.CONNECTION_CLIENT;

public class Util {
    public static String generateURLConnection(String clientIp){
        return "//" + clientIp + CONNECTION_CLIENT;
    }
}
