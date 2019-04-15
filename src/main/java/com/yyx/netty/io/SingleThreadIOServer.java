package com.yyx.netty.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

/**
 * @Description:
 * @Auther: yinyuxin
 * @Date: 2019年4月4日16:05:21
 */
public class SingleThreadIOServer {

    private String port;

    public SingleThreadIOServer(String port) {
        this.port = port;
    }

    public void startServer() {

        ServerSocket serverSocket = null;
        int i = 0;
        System.out.println("socket服务器在端口:"+port+" 等待客户端访问");
        try {
            serverSocket = new ServerSocket(Integer.valueOf(port));
            while (true) {
                Socket clientSocket = serverSocket.accept();
                handleRequest(clientSocket, i++);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void handleRequest(Socket clientSocket, int clientNo) {
        PrintStream printStream = null;
        BufferedReader bufferedReader = null;
        try {
            printStream = new PrintStream(clientSocket.getOutputStream());
            bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputString = null;
            while ((inputString = bufferedReader.readLine()) != null) {
                //输入“ESC”退出
                if (Objects.equals("ESC", inputString)) {
                    System.out.println("关闭与客户端:" + clientNo + "的连接");
                    printStream.close();
                    bufferedReader.close();
                    clientSocket.close();
                    break;
                } else {
                    System.out.println("来自客户端:" + clientNo + "的消息:" + inputString);
                    printStream.println(inputString);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        new SingleThreadIOServer("8080").startServer();
    }

}
