package com.github.takzhanov.stepic.hw07;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static com.github.takzhanov.stepic.hw07.MultiEchoServer.PORT;

public class MultiClient {

    public static void main(String[] args) throws IOException, InterruptedException {
        final int K_OF_LOAD = 10;

        for (int i = 0; i < K_OF_LOAD; i++) {
            new Thread(() -> {
                try {
                    Socket socket = new Socket("localhost", PORT);
                    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                    int i1 = 0;
                    while (i1 < 5000) {
                        dos.writeUTF(String.valueOf(i1++));
                        Thread.sleep(1);
                    }
                    System.out.println("Timeout. Closed");
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

    }
}
