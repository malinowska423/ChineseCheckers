package com.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread implements Runnable{

    @Override
    public void run() {

        try {
            Socket clientSocket = new Socket("localhost", 1024);

            System.out.println("Connected");

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            out.println("port-request");

            String message = "";
            int port = 0;
            while ((message = in.readLine()) != null) {
                System.out.println(message);
                if(message.charAt(0) == 'p') {
                    message = message.substring(1);
                    port = Integer.parseInt(message);
                    out.println("pend");
                }
                if(message.equals("disconnected")) {
                    break;
                }
            }

            System.out.println(port);

            clientSocket.close();

//            clientSocket = new Socket("localhost", port);



        } catch (IOException ex) {
            System.out.println("Exception");
        }

    }
}
