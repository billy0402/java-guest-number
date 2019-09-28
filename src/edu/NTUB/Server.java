package edu.NTUB;

import java.io.*;
import java.net.*;
import java.util.regex.*;

public class Server {
    public static void main() {
        int port = 9487;
        String question = "";
        int times = 0;
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server is created. Waiting for connect...");
            Socket server = serverSocket.accept();
            System.out.println("Client is connected. IP: " + server.getInetAddress());

            DataInputStream networkIn = new DataInputStream(server.getInputStream());
            DataOutputStream networkOut = new DataOutputStream(server.getOutputStream());

            while (true) {
                System.out.println("請輸入題目(四個不重複的數字)");
                String inputStr = userInput.readLine();

                question = checkInput(inputStr);

                if (!question.isEmpty()) {
                    System.out.println("這次遊戲題目為：" + question);
                    break;
                }
            }

            while (true) {
                networkOut.writeUTF("請輸入猜測數字(四個不重複的數字)");

                String getClientAnswer = new String(networkIn.readUTF());
                times++;
                System.out.println("目前為第" + times + "次猜");
                System.out.println("使用者輸入了：" + getClientAnswer);
                String answer = checkInput(getClientAnswer);

                if (!answer.isEmpty()) {
                    String result = checkAnswer(answer, question);
                    System.out.println(result);

                    networkOut.writeUTF(result);
                    if (result.equals("4A0B")) {
                        System.out.println("被猜中了，遊戲結束");
                        break;
                    }
                } else {
                    networkOut.writeUTF("輸入錯誤，請重新輸入");
                }
            }

            server.close();
            System.out.println("Server is closed.");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private static String checkInput(String inputStr) {
        String inputNum = "";
        boolean match = Pattern.matches("[0-9]{4}", inputStr);

        if (match) {
            boolean repeat = false;
            char[] charArray = inputStr.toCharArray();

            checkRepeat:
            for (int i = 0; i < charArray.length; i++) {
                for (int j = 0; j < charArray.length; j++) {
                    if (i != j) {
                        if (charArray[i] == charArray[j]) {
                            repeat = true;
                            break checkRepeat;
                        }
                    }
                }
            }

            if (repeat) {
                System.out.println("輸入重複，請重新輸入");
            } else {
                inputNum = inputStr;
            }
        } else {
            System.out.println("輸入錯誤，請重新輸入");
        }

        return inputNum;
    }

    private static String checkAnswer(String answer, String question) {
        char[] answerCharArray = answer.toCharArray();
        char[] questionCharArray = question.toCharArray();
        int A = 0; // 數字對位置對
        int B = 0; // 數字對位置錯

        for (int i = 0; i < answerCharArray.length; i++) {
            for (int j = 0; j < questionCharArray.length; j++) {
                if (answerCharArray[i] == questionCharArray[j]) {
                    if (i == j) {
                        A++;
                    } else {
                        B++;
                    }
                }
            }
        }

        return A + "A" + B + "B";
    }
}