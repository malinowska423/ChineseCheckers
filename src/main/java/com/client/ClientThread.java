package com.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Objects;

public class ClientThread implements Runnable{

    private static PrintWriter out;
    private static BufferedReader in;
    private static Socket clientSocket;

    public ClientThread() throws ChineseCheckersException {
        try {
            clientSocket = new Socket("localhost", 7554);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            throw new ChineseCheckersException(e.getMessage());
        }
    }

    @Override
    public void run() {
    }

    public static synchronized String sendMessage(String message) throws ChineseCheckersException {
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

    public static synchronized String receiveMessage() throws ChineseCheckersException{
        try {
            return in.readLine();
        } catch (IOException e) {
            throw new ChineseCheckersException(e.getMessage());
        }
    }

    public static void closeSocket() throws ChineseCheckersException {
        ClientThread.sendMessage("close");
        try {
            clientSocket.close();
            System.exit(0);
        } catch (IOException e) {
            throw new ChineseCheckersException(e.getMessage());
        }
    }
}
