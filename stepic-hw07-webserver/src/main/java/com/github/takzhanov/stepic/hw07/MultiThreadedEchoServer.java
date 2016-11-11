package com.github.takzhanov.stepic.hw07;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.github.takzhanov.stepic.hw07.Constants.*;

public class MultiThreadedEchoServer extends EchoServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        MultiThreadedEchoServer server = new MultiThreadedEchoServer(SERVER_PORT);
        new Thread(server).start();
        Thread.sleep(SERVER_START_WAIT_TIME);
        System.out.println("Server started");//для теста
        Thread.sleep(SERVER_MAX_LIFE_TIME);
        server.stop();
    }

    public MultiThreadedEchoServer(int port) {
        super(port);
    }

    @Override
    public void run() {
        openServerSocket();
        System.out.printf("ServerThread Started on port: %d%n", serverPort);
        ExecutorService executorService = Executors.newFixedThreadPool(Constants.SERVER_N_THREADS);
        while (!isStopped()) {
            Socket clientSocket = acceptClientSocket();
            if (clientSocket == null) {
                return;
            }
            executorService.submit(() -> {
                processClientRequest(clientSocket);
            });
        }
        try {
            executorService.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException("Error awaiting termination", e);
        }
        executorService.shutdown();
        System.out.println("Server Stopped");
    }

}