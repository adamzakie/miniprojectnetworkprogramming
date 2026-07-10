/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chat.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class ChatServer {
    
    // Thread-safe collection to maintain a list of all active client output streams
    private static final Set<PrintWriter> clientWriters = new CopyOnWriteArraySet<>();

    public static void main(String[] args) {
        System.out.println("Chat Server is starting up...");
        
        // Open a ServerSocket on port 5000 using try-with-resources to prevent memory leaks
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server is actively listening on port 5000...");

            // Implement a while(true) loop to constantly accept() incoming client connections
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                // Spawn a new Thread for every client that connects
                ClientHandler handler = new ClientHandler(clientSocket, clientWriters);
                new Thread(handler).start();
            }
        } catch (IOException e) {
            System.err.println("Critical Server Exception: " + e.getMessage());
        }
    }
}