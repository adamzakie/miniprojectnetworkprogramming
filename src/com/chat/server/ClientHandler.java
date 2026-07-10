/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chat.server;
import com.chat.util.EncryptionUtils;
import java.io.*;
import java.net.Socket;
import java.util.Set;

public class ClientHandler implements Runnable {
    private Socket socket;
    private Set<PrintWriter> clientWriters;
    private PrintWriter out;
    private BufferedReader in;

    public ClientHandler(Socket socket, Set<PrintWriter> clientWriters) {
        this.socket = socket;
        this.clientWriters = clientWriters;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            
            // Authentication: Must match client
            String password = in.readLine();
            if (!"MySecretPassword".equals(password)) {
                socket.close(); return;
            }
            
            clientWriters.add(out);
            String encryptedIncoming;
            while ((encryptedIncoming = in.readLine()) != null) {
                String decrypted = EncryptionUtils.decrypt(encryptedIncoming);
                String broadcast = "Client: " + decrypted;
                for (PrintWriter writer : clientWriters) {
                    writer.println(EncryptionUtils.encrypt(broadcast));
                }
            }
        } catch (Exception e) { e.printStackTrace(); } 
        finally { clientWriters.remove(out); try { socket.close(); } catch (IOException e) {} }
    }
}