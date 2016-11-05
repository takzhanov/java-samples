package com.github.takzhanov.stepic.hw07;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MultiClient {

    public static final int PORT = 5060;

    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket("localhost", PORT);
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        int i = 0;
        while (i < 5000) {
            dos.writeUTF(String.valueOf(i++));
            Thread.sleep(1);
        }
        System.out.println("Timeout");
        socket.close();
    }
}
