Secure Multi-Client Chat Application
A secure, multi-threaded Java chat application featuring AES encryption and client authentication.

🚀 Features
Multi-Client Support: Handles multiple users simultaneously using multithreading.

AES Encryption: All messages are encrypted using AES before transmission to ensure secure communication.

Authentication: Implements a password handshake mechanism to verify clients upon connection.

GUI Interface: User-friendly interface built with Java Swing for a seamless chat experience.

🛠️ Project Structure
com.chat.client: Contains ChatUI.java for the user interface and client logic.

com.chat.server: Contains ChatServer.java and ClientHandler.java for server-side management.

com.chat.util: Contains EncryptionUtils.java for all security and encryption functions.

📋 How to Run
Launch the Server: Run com.chat.server.ChatServer first.

Launch Clients: Run com.chat.client.ChatUI for as many clients as you wish to simulate.

Connect: In each ChatUI window, click the "Connect & Start" button.

Chat: Type your messages and click "Send" to broadcast them securely to other connected clients.

🔒 Security Information
Encryption Algorithm: AES (Advanced Encryption Standard).

Authentication: A hardcoded handshake mechanism is performed immediately upon socket connection.

👥 Team & Contribution
Development: [Insert Names Here]

Tools Used: Java, NetBeans, Git/GitHub, Java Swing.
