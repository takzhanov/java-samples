package com.github.takzhanov.stepic.hw07;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EchoRequestProcessor implements RequestProcessor {
    public void processClientRequest(Socket clientSocket) {
        long startTime = System.currentTimeMillis();
        System.out.printf("%s start processing new client request%n", Thread.currentThread());
        try (BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true)) {
            String inputLine;
            while ((inputLine = input.readLine()) != null) {
//                System.out.println(inputLine);
                output.println(inputLine);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error processing client request", e);
        }
        long finishTime = System.currentTimeMillis();
        System.out.printf("%s finish processing client request: %ss%n", Thread.currentThread(), (finishTime - startTime) / 1000f);
    }
}
