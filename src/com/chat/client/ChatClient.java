/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chat.client;
import com.chat.util.EncryptionUtils;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5000);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner sc = new Scanner(System.in);

            // Send password for authentication
            out.println("MySecretPassword");

            // Receive thread
            new Thread(() -> {
                try {
                    String line;
                    while ((line = in.readLine()) != null) {
                        System.out.println(EncryptionUtils.decrypt(line));
                    }
                } catch (Exception e) { e.printStackTrace(); }
            }).start();

            // Send loop
            while (true) {
                out.println(EncryptionUtils.encrypt(sc.nextLine()));
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}