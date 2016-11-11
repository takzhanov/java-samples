package com.github.takzhanov.stepic.hw07;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadedServer implements Runnable {

    private final int serverPort;
    private volatile boolean isStopped = false;
    private ServerSocket serverSocket;

    public MultiThreadedServer(int serverPort) {
        this.serverPort = serverPort;
    }

    @Override
    public void run() {
//        openServerSocket();
////        System.out.println("Server started");
////        Long start = System.currentTimeMillis();
//        while (!isStopped()) {
//            try (Socket clientSocket = serverSocket.accept()) {
//                new SocketProcessor(serverSocket.accept()).start();
//            } catch (IOException e) {
//                if (isStopped) {
//
//                }
//            } fi
//            System.out.println("New thread started");
//        }
//        System.out.println("Timeout");
//        ss.close();
    }

    private synchronized void openServerSocket() {

    }

    public synchronized void stop() {
        this.isStopped = true;
        try {
            this.serverSocket.close();
        }catch (IOException e) {

        }
    }
}
