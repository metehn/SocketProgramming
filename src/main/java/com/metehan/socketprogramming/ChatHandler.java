package com.metehan.socketprogramming;

import java.io.*;
import java.net.Socket;

public class ChatHandler extends Thread {

    private ChatServer server;
    BufferedReader reader;
    BufferedWriter writer;

    public ChatHandler(ChatServer server, Socket socket) throws IOException {
        this.server = server;

        InputStream in = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(in);
        reader = new BufferedReader(isr);

        OutputStream out = socket.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(out);
        writer = new BufferedWriter(osw);
        //printstream

    }


    @Override
    public void run() {

        while (true) {
            try {
                String request = reader.readLine();
                System.out.println("İstemciden gelen istek "+ request);
                if(request ==null ){
                    throw new Exception();
                }
                server.send(this, request);
            } catch (Exception e) {
                System.out.println("İstemci bağlantıyı kopardı");
                server.getHandlerList().remove(this);
                break;
            }

        }

    }

    public void send(String response){
        try {
            writer.write(response+"\r\n");
            writer.flush();
            System.out.println("İstemciye gönderilen istek " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
