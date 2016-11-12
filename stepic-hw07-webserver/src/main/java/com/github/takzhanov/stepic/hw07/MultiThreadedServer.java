package com.github.takzhanov.stepic.hw07;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.github.takzhanov.stepic.hw07.Constants.SERVER_PORT;

public class MultiThreadedServer extends Server {

    public static void main(String[] args) throws IOException, InterruptedException {
        MultiThreadedServer server = new MultiThreadedServer(new EchoRequestProcessor(), SERVER_PORT);
        new Thread(server).start();
//        Thread.sleep(SERVER_MAX_LIFE_TIME);
//        server.stop();
    }

    public MultiThreadedServer(int port) {
        super(port);
    }

    public MultiThreadedServer(RequestProcessor rp, int port) {
        super(rp, port);
    }

    @Override
    public void run() {
        openServerSocket();
        System.out.printf("MainServerThread started on port: %d%n", serverPort);
        System.out.println("Server started");//маркер для теста
        ExecutorService executorService = Executors.newFixedThreadPool(Constants.N_SERVER_THREADS);
        while (!isStopped()) {
            Socket clientSocket = acceptClientSocket();
            if (clientSocket == null) {
                return;
            }
            executorService.submit(() -> getRequestProcessor().processClientRequest(clientSocket));
        }
        try {
            executorService.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException("Error awaiting termination", e);
        }
        executorService.shutdown();
        System.out.println("MainServerThread stopped");
    }

}