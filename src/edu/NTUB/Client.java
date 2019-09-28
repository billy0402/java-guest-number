package edu.NTUB;

import java.io.*;
import java.net.*;

public class Client {
    public static void main() {
        String serverIP = "127.0.0.1";
        int port = 9487;
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        try {
            Socket client = new Socket(serverIP, port);
            System.out.println("Connect successful.");

            DataInputStream networkIn = new DataInputStream(client.getInputStream());
            DataOutputStream networkOut = new DataOutputStream(client.getOutputStream());

            while (true) {
                String getServerMsg = new String(networkIn.readUTF());
                System.out.println(getServerMsg);

                String answer = userInput.readLine();
                networkOut.writeUTF(answer);

                String getServerResult = new String(networkIn.readUTF());
                System.out.println("結果：" + getServerResult);

                if (getServerResult.equals("4A0B")) {
                    System.out.println("答案正確，遊戲結束");
                    break;
                }
            }

            client.close();
            System.out.println("Client is closed.");
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}