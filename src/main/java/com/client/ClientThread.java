package com.client;

import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class ClientThread implements Runnable{

    private static PrintWriter out;
    private static BufferedReader in;
    private static Socket clientSocket;

    ClientThread() throws ChineseCheckersException {
        try {
            clientSocket = new Socket("25.87.187.125", 7554);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            throw new ChineseCheckersException(e.getMessage());
        }
    }

    @Override
    public void run() {
    }

    static synchronized String sendRequest(String message) throws ChineseCheckersException {
        out.println(message);
        if (!message.equals("close")) {
            try {
                String answer = in.readLine();
                String [] code = answer.split(";");
                if (code[0].equals("error")){
                    throw new ChineseCheckersException(code[1]);
                } else {
                    return answer;
                }
            } catch (SocketException e) {
                new ChineseCheckersWindowException(e.getMessage()).showWindow();
                System.exit(1);
                return "";
            } catch (IOException e){
                e.printStackTrace();
                throw new ChineseCheckersException(e.getMessage());
            }
        } else {
            return "disconnected";
        }
    }

    static void sendMessage(String message) {
        out.println(message);
    }

    static synchronized String receiveMessage() throws ChineseCheckersException{
        try {
            return in.readLine();
        } catch (SocketException e) {
            Platform.runLater(() -> {
                new ChineseCheckersWindowException(e.getMessage()).showWindow();
                System.exit(1);
            });
            return "";
        } catch (IOException e) {
            throw new ChineseCheckersException(e.getMessage());
        }
    }

    static void closeSocket() throws ChineseCheckersException {
        ClientThread.sendRequest("close");
        try {
            clientSocket.close();
            System.exit(0);
        } catch (IOException e) {
            throw new ChineseCheckersException(e.getMessage());
        }
    }
}
