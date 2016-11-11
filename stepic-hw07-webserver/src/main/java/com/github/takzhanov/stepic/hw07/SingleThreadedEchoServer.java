package com.github.takzhanov.stepic.hw07;

import java.io.IOException;
import java.net.Socket;

import static com.github.takzhanov.stepic.hw07.Constants.SERVER_MAX_LIFE_TIME;
import static com.github.takzhanov.stepic.hw07.Constants.SERVER_PORT;

public class SingleThreadedEchoServer extends EchoServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        SingleThreadedEchoServer server = new SingleThreadedEchoServer(SERVER_PORT);
        new Thread(server).start();
        Thread.sleep(SERVER_MAX_LIFE_TIME);
        server.stop();
    }

    public SingleThreadedEchoServer(int port) {
        super(port);
    }

    @Override
    public void run() {
        openServerSocket();
        System.out.printf("ServerThread Started on port: %d%n", serverPort);
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
            processClientRequest(clientSocket);
        }
        System.out.println("ServerThread Stopped");
    }

}