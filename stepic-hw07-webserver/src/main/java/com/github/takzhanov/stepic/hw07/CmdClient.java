package com.github.takzhanov.stepic.hw07;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static com.github.takzhanov.stepic.hw07.Constants.*;

public class CmdClient {

    /**
     * @param args text times period
     * @throws IOException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length < 3) {
            System.out.println("Required 3 parameters: text count pause");
            return;
        }
        String text = args[0];
        int times = Integer.parseInt(args[1]);
        int period = Integer.parseInt(args[2]);

        for (int i = 0; i < times; i++) {
            try (Socket socket = new Socket("localhost", SERVER_PORT);
                 PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String userInput;
                String serverAnswer;
                userInput = text;
                //System.out.println("Client(" + Thread.currentThread() + "): " + userInput);
                output.println(userInput);
                long startTime = System.currentTimeMillis();
                serverAnswer = input.readLine();
                long finishTime = System.currentTimeMillis();
                //System.out.println("Server(" + Thread.currentThread() + "): " + input.readLine());
                System.out.printf("Waited answer(%s): %ss%n", Thread.currentThread(), (finishTime - startTime) / 1000f);
                Thread.sleep(period);
            } catch (IOException e) {
                throw new RuntimeException("Connection ex", e);
            }
        }
    }
}
