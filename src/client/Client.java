package client;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Client extends JFrame{
    private static final int PORT = 6789;

    private JTextField userText;
    private JTextArea chatWindow;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private String message = "";
    private String serverIP;
    private Socket connection;

    public static void main(String[] args) {
        String localHost = "127.0.0.1";
        String remoteHost = "192.168.50.44";

        Client client = new Client(remoteHost);
        client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.startRunning();
    }


    public Client(String host) {
        super("Client title name!");
        serverIP = host;
        userText = new JTextField();
        userText.setEditable(false);
        userText.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    sendMessage(event.getActionCommand());
                    userText.setText("");
                }
            }
        );
        add(userText, BorderLayout.NORTH);
        chatWindow = new JTextArea();
        add(new JScrollPane(chatWindow), BorderLayout.CENTER);
        setSize(300, 150);
        setVisible(true);
    }


    public void startRunning() {
        try {
            connectToServer();
            setUpStreams();
            whileChatting();
        } catch (EOFException e) {
            showMessage("\n Client terminated connection");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeCrap();
        }
    }

    private void connectToServer() throws IOException {
        showMessage("Attempting connection... \n");
        connection = new Socket(InetAddress.getByName(serverIP), PORT);
        showMessage("Connected to: " + connection.getInetAddress().getHostName());
    }

    private void setUpStreams() throws IOException {
        outputStream = new ObjectOutputStream(connection.getOutputStream()); // client to server
        outputStream.flush();
        inputStream = new ObjectInputStream(connection.getInputStream());
        showMessage("\nDude your streams are now good to go! \n");
    }

    private void whileChatting() throws IOException {
        ableToType(true);
        do {
            try {
                message = (String) inputStream.readObject();
                showMessage("\n" + message);
            } catch (ClassNotFoundException e) {
                showMessage("\n I don't know that object type...");
            }
        } while (!message.equals("SERVER - END"));
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

    // sends message to the server
    private void sendMessage(String message) {
        try {
            outputStream.writeObject("CLIENT - " + message);
            outputStream.flush();
            showMessage("\nCLIENT - " + message);
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
