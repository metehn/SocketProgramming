/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.metehan.socketprogramming;

/**
 *
 * @author metehan
 */
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class ChatServer extends Thread {

    private ServerSocket serverSocket;
    private List<ChatHandler> handlerList = new ArrayList<>();

    public ChatServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        System.out.println("Sunucu başlatıldı. Port: " + serverSocket.getLocalPort());
        while (true) {

            try {
                Socket socket = serverSocket.accept();
                System.out.println("Bir istemci bağlandı ");
                ChatHandler handler = new ChatHandler(this, socket);
                handlerList.add(handler);
                handler.start();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public void send(ChatHandler sender, String message) {

        for (ChatHandler handler : handlerList) {

            if (handler != sender) {
                handler.send(message);
            }

        }

    }

    public static void main(String[] args) throws IOException {
        int port = 5555;
        ChatServer server = new ChatServer(port);
        server.start();
    }

}
