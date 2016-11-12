package com.github.takzhanov.stepic.hw07;

import java.net.Socket;

@FunctionalInterface
public interface RequestProcessor {
    void processClientRequest(Socket clientSocket);
}
