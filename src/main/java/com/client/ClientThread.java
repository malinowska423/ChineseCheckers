package com.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

public class ClientThread implements Runnable{

    private static PrintWriter out;
    private static BufferedReader in;
    private static Socket clientSocket;

    public ClientThread() {
        try {
            clientSocket = new Socket("localhost", 1024);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void run() {
        System.out.println("Connected");
        int port = Integer.parseInt(ClientThread.sendMessage("port-request"));
        System.out.println(port);
    }

    public static String sendMessage(String message) {
        //TODO: make this method throw ChineseCheckersException
        out.println(message);
        String answer;
        try {
            answer = in.readLine();
            return Objects.requireNonNullElse(answer, "");
        } catch (IOException e) {
            return "";
        }
    }

    public static void closeSocket() {
        ClientThread.sendMessage("pend");
        try {
            clientSocket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
