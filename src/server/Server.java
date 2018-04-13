package server;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Server extends JFrame{
    private static final int PORT = 6789;

    private JTextField userText;
    private JTextArea chatWindow;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private ServerSocket server;
    private Socket connection;


    public static void main(String[] args) {
        Server server = new Server();
        server.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        server.startRunning();
    }


    public Server() {
        super("Title at top of window");
        userText = new JTextField();
        userText.setEditable(false);
        userText.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent event){
                    sendMessage(event.getActionCommand());
                    userText.setText("");
                }
            }
        );
        add(userText, BorderLayout.NORTH);
        chatWindow = new JTextArea();
        add(new JScrollPane(chatWindow));
        setSize(300,150);
        setVisible(true);
    }

    public void startRunning() {
        try {
            server = new ServerSocket(PORT, 100);
            while(true) {
                try {
                    waitForConnection();
                    setupStreams();
                    whileChatting();
                } catch (EOFException e) {
                    showMessage("\n server.Server ended the connection!");
                } finally {
                    closeCrap();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void waitForConnection() throws IOException{
        showMessage(" Waiting for someone to connect...\n");
        connection = server.accept();
        showMessage(" Now connected to "+connection.getInetAddress().getHostName()+"\n");
    }

    private void setupStreams() throws IOException{
        // sending away!
        outputStream = new ObjectOutputStream(connection.getOutputStream());
        outputStream.flush();

        // coming in!
        inputStream = new ObjectInputStream(connection.getInputStream());
        showMessage("\n Streams are now setup!\n");
    }

    private void whileChatting() throws IOException {
        String message = " You are now connected! ";
        showMessage(message);
        ableToType(true);
        do {
            // have a conversation
            try {
                message = (String) inputStream.readObject();
                showMessage("\n" + message);
            } catch (ClassNotFoundException e) {
                showMessage("\n idk wtf that user sent!");
            }
        } while(!message.equals("CLIENT - END"));
    }

    private void closeCrap() {
        showMessage("\n Closing connections... \n");
        ableToType(false);
        try {
            outputStream.close();
            inputStream.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // sends a message to client
    private void sendMessage(String message) {
        try {
            outputStream.writeObject("SERVER - " + message);
            outputStream.flush();
            showMessage("\nSERVER - " + message);
        } catch (IOException e) {
            chatWindow.append("\n ERROR: DUDE I CANT SEND THAT MESSAGE");
        }
    }

    private void showMessage(final String text) {
        SwingUtilities.invokeLater(
            new Runnable() {
                public void run() {
                    chatWindow.append(text);
                }
            }
        );
    }

    private void ableToType(final boolean tof) {
        SwingUtilities.invokeLater(
            new Runnable() {
                public void run() {
                    userText.setEditable(tof);
                }
            }
        );
    }

}




















