package com.github.takzhanov.stepic.hw07;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public abstract class Server implements Runnable {
    protected int serverPort;
    private ServerSocket serverSocket = null;
    private boolean isStopped = false;
    private RequestProcessor requestProcessor;

    public Server(int port) {
        this(new EchoRequestProcessor(), port);
    }

    public Server(RequestProcessor rp, int port) {
        this.requestProcessor = rp;
        this.serverPort = port;
    }

    public RequestProcessor getRequestProcessor() {
        return requestProcessor;
    }

    protected boolean isStopped() {
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

    protected void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Cannot open port %s", serverPort), e);
        }
    }

    protected Socket acceptClientSocket() {
        try {
            return serverSocket.accept();
        } catch (IOException e) {
            if (isStopped()) {
                System.out.println("Server stopped");
                return null;
            }
            throw new RuntimeException("Error accepting client connection", e);
        }
    }
}
