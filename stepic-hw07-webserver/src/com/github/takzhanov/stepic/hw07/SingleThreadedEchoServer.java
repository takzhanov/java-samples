package com.github.takzhanov.stepic.hw07;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static com.github.takzhanov.stepic.hw07.Constants.SERVER_PORT;
import static com.github.takzhanov.stepic.hw07.Constants.SERVER_MAX_LIFE_TIME;

public class SingleThreadedEchoServer implements Runnable {

    public static void main(String[] args) throws IOException, InterruptedException {
        SingleThreadedServer server = new SingleThreadedServer(SERVER_PORT);
        new Thread(server).start();
        Thread.sleep(SERVER_MAX_LIFE_TIME);
        server.stop();
    }

    private int serverPort;
    private ServerSocket serverSocket = null;
    private boolean isStopped = false;

    public SingleThreadedEchoServer(int port) {
        this.serverPort = port;
    }

    public void run() {
        openServerSocket();
        System.out.println(String.format("Server Started on port %s", serverPort));
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
        System.out.println("Server Stopped");
    }

    private void processClientRequest(Socket clientSocket) {
        try (BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true)) {
            long time = System.currentTimeMillis();
            String inputLine;
            while ((inputLine = input.readLine()) != null) {
                output.println(inputLine);
            }
            System.out.println("Request processed: " + time);
        } catch (IOException e) {
            throw new RuntimeException("Error processing client request");
        }
    }

    private boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop() {
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Cannot open port %s", serverPort), e);
        }
    }
}