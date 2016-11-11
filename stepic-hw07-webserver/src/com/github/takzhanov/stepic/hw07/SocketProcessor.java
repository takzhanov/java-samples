package com.github.takzhanov.stepic.hw07;

import java.io.*;
import java.net.Socket;

public class SocketProcessor implements Runnable {

    private final Socket socket;
    private final InputStream is;
    private final OutputStream os;

    public SocketProcessor(Socket socket) throws IOException {
        this.socket = socket;
        this.is = socket.getInputStream();
        this.os = socket.getOutputStream();
    }

    @Override
    public void run() {
        DataInputStream dis = new DataInputStream(new BufferedInputStream(is));
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(os));
        try {
            while (true) {
                String line = dis.readUTF();
                if ("bye".equalsIgnoreCase(line)) {
                    break;
                } else {
                    System.out.println(line);
                    dos.writeUTF(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
