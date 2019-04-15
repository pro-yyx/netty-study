package com.yyx.netty.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

public class SingleThreadIOClient {

    public static void main(String[] args) {
        Socket socket = null;
        PrintWriter printWriter = null;
        BufferedReader bufferedReader = null;
        try {
            socket = new Socket("127.0.0.1", 8080);
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("......连接到服务器");
            System.out.println("请输入数据[输入‘ESC’退出]");
            BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(System.in));
            String userInput = null;
            while ((userInput = bufferedReader1.readLine()) != null) {
                printWriter.print(userInput);
                System.out.println(bufferedReader.read());
                if (Objects.equals("ESC", userInput)) {
                    System.out.println("退出客户端......");
                    printWriter.close();
                    bufferedReader.close();
                    bufferedReader1.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
