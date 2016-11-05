package com.github.takzhanov.stepic.hw07;

import java.io.IOException;
import java.net.ServerSocket;

public class MultiEchoServer {

    public static final int PORT = 5060;

    public static void main(String[] args) throws IOException {
        System.out.println("Server started");
        ServerSocket ss = new ServerSocket(PORT);
        Long start = System.currentTimeMillis();
        while (start + 15000 > System.currentTimeMillis()) {
            new Thread(new SocketProcessor(ss.accept())).start();
            System.out.println("New thread started");
        }
        System.out.println("Timeout");
        ss.close();
    }
}
