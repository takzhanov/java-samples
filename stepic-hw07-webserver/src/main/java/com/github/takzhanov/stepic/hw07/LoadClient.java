package com.github.takzhanov.stepic.hw07;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static com.github.takzhanov.stepic.hw07.Constants.*;

public class LoadClient {

    public static void main(String[] args) throws IOException, InterruptedException {
        for (int i = 0; i < N_CLIENT; i++) {
            new Thread(() -> {
                long startTime = System.currentTimeMillis();
                try (Socket socket = new Socket("localhost", SERVER_PORT);
                     PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                     BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    String userInput;
                    String serverAnswer;
                    for (int j = 0; j < K_OF_LOAD; j++) {
                        userInput = String.valueOf(j);
//                        System.out.println("Client(" + Thread.currentThread() + "): " + userInput);
                        output.println(userInput);
                        serverAnswer = input.readLine();
//                        System.out.println("Server(" + Thread.currentThread() + "): " + input.readLine());
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Connection ex", e);
                }
                long finishTime = System.currentTimeMillis();
                System.out.printf("Waited answer(%s): %ss%n", Thread.currentThread(), (finishTime - startTime) / 1000f);
            }).start();
        }
    }
}
