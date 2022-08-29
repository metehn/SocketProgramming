/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.metehan.socketprogramming;

/**
 *
 * @author metehan
 */
import java.io.*;
import java.net.Socket;

public class ChatClient extends Thread {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public ChatClient(String host, int port) throws IOException {
        socket = new Socket(host, port);

        InputStream in = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(in);
        reader = new BufferedReader(isr);

        OutputStream out = socket.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(out);
        writer = new BufferedWriter(osw);

    }

    @Override
    public void run() {

        while (true) {
            try {
                String response = reader.readLine();
                System.out.println("Sunucudan gelen yanıt " + response);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String response){
        try {
            writer.write(response+"\r\n");
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        String host = "localhost";
        int port = 5555;
        ChatClient client = new ChatClient(host, port);
        client.start();

        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(isr);

        while(true){

            String message = reader.readLine();
            client.send(message);
            System.out.println("Sunucuya gönderildi "+message);
        }

    }

}
