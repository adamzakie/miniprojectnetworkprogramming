/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chat.client;

import com.chat.util.EncryptionUtils;
import java.io.*;
import java.net.Socket;
import javax.swing.*;
import java.awt.*;

public class ChatUI extends JFrame {
    private JTextArea chatArea = new JTextArea();
    private JTextField inputField = new JTextField();
    private JButton sendButton = new JButton("Send");
    private JButton connectButton = new JButton("Connect & Start");
    private PrintWriter out;
    private BufferedReader in;

    public ChatUI() {
        // Setup GUI Layout
        setLayout(new BorderLayout());
        add(new JScrollPane(chatArea), BorderLayout.CENTER);
        
        JPanel p = new JPanel(new BorderLayout());
        p.add(inputField, BorderLayout.CENTER);
        p.add(sendButton, BorderLayout.EAST);
        add(p, BorderLayout.SOUTH);
        add(connectButton, BorderLayout.NORTH); // Add connect button
        
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        // Action Listeners
        connectButton.addActionListener(e -> {
            connectToServer();
            connectButton.setEnabled(false); // Disable after connecting
        });
        
        sendButton.addActionListener(e -> sendMessage());
    }

    private void connectToServer() {
        try {
            Socket socket = new Socket("localhost", 5000);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            // Authentication handshake [cite: 23]
            out.println("MySecretPassword"); 

            // Background thread to listen for incoming messages [cite: 4]
            new Thread(() -> {
                try {
                    String line;
                    while ((line = in.readLine()) != null) {
                        // Decrypt message upon receipt [cite: 25]
                        chatArea.append(EncryptionUtils.decrypt(line) + "\n");
                    }
                } catch (Exception e) { chatArea.append("Disconnected.\n"); }
            }).start();
            
            chatArea.append("Connected to server!\n");
        } catch (Exception e) { 
            chatArea.append("Connection failed: " + e.getMessage() + "\n"); 
        }
    }

    private void sendMessage() {
        try {
            // Encrypt message before sending [cite: 25]
            out.println(EncryptionUtils.encrypt(inputField.getText()));
            inputField.setText("");
        } catch (Exception e) { e.printStackTrace(); }
    }

    // MAIN METHOD: This solves your error
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChatUI());
    }
}