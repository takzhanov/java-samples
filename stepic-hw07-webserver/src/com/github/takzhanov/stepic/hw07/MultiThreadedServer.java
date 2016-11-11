package com.github.takzhanov.stepic.hw07;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static com.github.takzhanov.stepic.hw07.Constants.SERVER_MAX_LIFE_TIME;
import static com.github.takzhanov.stepic.hw07.Constants.SERVER_PORT;

public class MultiThreadedServer  extends EchoServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        MultiThreadedServer server = new MultiThreadedServer(SERVER_PORT);
        new Thread(server).start();
        Thread.sleep(SERVER_MAX_LIFE_TIME);
        server.stop();
    }

    public MultiThreadedServer(int port) {
        super(port);
    }

    @Override
    public void run() {
        openServerSocket();
        System.out.printf("Server Started on port: %d%n", serverPort);
        while (!isStopped()) {
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                if (isStopped()) {
                    System.out.println("Server Stopped");
                    return;
                }
                throw new RuntimeException("Error accepting client connection", e);
            }
            Socket finalClientSocket = clientSocket;
            new Thread(()-> {
                processClientRequest(finalClientSocket);
            }).start();
        }
        System.out.println("Server Stopped");
    }

}