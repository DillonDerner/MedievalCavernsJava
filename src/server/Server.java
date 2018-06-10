package server;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

import common.*;
import common.enums.RequestType;
import common.enums.ResponseType;
import common.enums.TileType;

public class Server extends JFrame{

    private static final int PORT = 6789;

    private JTextField userText;
    private JTextArea chatWindow;

    private List<ObjectOutputStream> outputStreams = new ArrayList<>();
    private ServerSocket serverSocket;

    public DataManager dataManager = new DataManager();

    public int totalConnections = 0;
    public int currentConnections = 0;


    public static void main(String[] args) {
        Server server = new Server(); // <-- creates server
        server.startServer(server);   // <-- starts server
    }

    public Server() {
        super("Medieval Caverns Java Socket Server");
        userText = new JTextField();
        userText.setEditable(false);
        userText.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent event){
                        sendMessageToAllClients(event.getActionCommand());
                        userText.setText("");
                    }
                }
        );
        add(userText, BorderLayout.SOUTH);
        chatWindow = new JTextArea();
        add(new JScrollPane(chatWindow));
        //chatWindow.setAutoscrolls(true);
        setSize(600,300);
        setVisible(true);
    }
    public void startServer(Server server) {
        server.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            serverSocket = new ServerSocket(PORT, 100);
            connectClients(server);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void connectClients(Server server) throws IOException {
        do {
            showMessage("Waiting for someone to connect...");
            Socket connection = serverSocket.accept();
            showMessage("User ["+connection.getInetAddress().getHostName()+"] has connected!\n");
            totalConnections ++;
            currentConnections ++;
            showMessage("Total Connections: " + totalConnections);
            showMessage("Current Connections: " + currentConnections);
            showMessage("");


            new Thread(new ServerThread(server, connection)).start();
        } while(true);
    }
    private void showMessage(final String text) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        chatWindow.append(text + "\n");
                    }
                }
        );
    }

    private void sendMessageToAllClients(String message) {
        try {
            ResponseDTO responseDTO = new ResponseDTO(ResponseType.SERVER, message);
            for (ObjectOutputStream outputStream : outputStreams) {
                outputStream.writeObject(responseDTO.toString());
                outputStream.flush();
            }
            showMessage("\n" + responseDTO.toString());
        } catch (IOException e) {
            chatWindow.append("\n ERROR: DUDE I CANT SEND THAT MESSAGE");
        }
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
    private void addOutputStream(ObjectOutputStream outputStream) {
        outputStreams.add(outputStream);
    }
    private void removeOutputStream(ObjectOutputStream outputStream) {
        outputStreams.remove(outputStream);
        currentConnections --;
        showMessage("Connection Closed.");
        showMessage("Current Connections: " + currentConnections);
        showMessage("");
    }

    public static class ServerThread implements Runnable {

        private Server server;
        private Socket connection;
        private ObjectOutputStream outputStream;
        private ObjectInputStream inputStream;

        private User user;
        private boolean isLoggedIn = false;

        //private RequestType lastRequest = RequestType.EMPTY;

        ServerThread(Server server, Socket connection){
            this.server = server;
            this.connection = connection;

        }
        public void run() {
            try {
                setupStreams(connection);
                System.out.println("Thread Started!");
                startRunning(connection);
                System.out.println("Thread Ended!");
            } catch (IOException e) {
                System.out.println("Thread died in Run() "+ e.getMessage());
            }

        }
        private void setupStreams(Socket connection) throws IOException{
            // sending away!
            outputStream = new ObjectOutputStream(connection.getOutputStream());
            outputStream.flush();

            // coming in!
            inputStream = new ObjectInputStream(connection.getInputStream());
            //server.showMessage("\n Streams are now setup!\n");
            server.addOutputStream(outputStream);
        }
        private void closeCrap(Socket connection) {
            try {
                outputStream.close();
                inputStream.close();
                connection.close();
                server.removeOutputStream(outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        private void startRunning(Socket connection) {
            try {
                while(true) {
                    try {
                        String message = "null";
                        server.ableToType(true);
                        do {
                            try {
                                // wait for client to send something
                                message = (String) inputStream.readObject();
                                handleRequest(message);

                            } catch (ClassNotFoundException e) {
                                server.showMessage("\n idk wtf that user sent!");
                                System.out.println("\n idk wtf that user sent!");
                            }
                        } while(!message.equals("[LOGOUT,,,,,]"));

                    } catch (EOFException e) {
                        server.showMessage("\n serverSocket.Server ended the connection!");
                    } finally {
                        if (isLoggedIn) {
                            server.showMessage("\nUser ["+ user.getUserName()+"] has quit!");
                            user = null;
                            isLoggedIn = false;
                        } else {
                            server.showMessage("\nUser ["+ connection.getInetAddress().getHostName()+"] has quit!");
                        }
                        closeCrap(connection);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        private void handleRequest(String message) {
            System.out.println("REQUEST RECEIVED: " + message);
            if(!message.startsWith("[")){
                server.showMessage("Client sent invalid request format: " +message);
                ResponseDTO responseDTO = new ResponseDTO(ResponseType.ERROR, "You sent an invalid request format: " + message);
                sendMessage(responseDTO);
            } else {
                message = message.replace("[", "");
                message = message.replace("]", "");
                List<String> requestList = Arrays.asList(message.split(","));
                try {
                    RequestDTO requestDTO = new RequestDTO();
                    RequestType requestType = RequestType.valueOf(requestList.get(0));
                    requestDTO.setRequestType(requestType);
                    if (requestList.size() >= 2)requestDTO.setValue1(requestList.get(1));
                    if (requestList.size() >= 3)requestDTO.setValue2(requestList.get(2));
                    if (requestList.size() >= 4)requestDTO.setValue3(requestList.get(3));
                    if (requestList.size() >= 5)requestDTO.setValue4(requestList.get(4));
                    if (requestList.size() >= 6)requestDTO.setValue5(requestList.get(5));

                    respondToRequest(requestDTO); //  <-- GAME LOGIC

                } catch(Exception e){
                    server.showMessage("Client sent invalid requestType: " +message);
                    ResponseDTO responseDTO = new ResponseDTO(ResponseType.ERROR, "You sent an invalid requestType: " + message);
                    sendMessage(responseDTO);
                }
            }
        }
        private void respondToRequest(RequestDTO requestDTO) {
            if     (requestDTO.getRequestType() == RequestType.LOGIN)          login(requestDTO);
            // TODO create a function that requires user to be logged into this thread before sending other requests
            else if(requestDTO.getRequestType() == RequestType.CREATE_ACCOUNT) createAccount(requestDTO);
            else if(requestDTO.getRequestType() == RequestType.SERVER_ERROR)   serverError(requestDTO);
            else if(requestDTO.getRequestType() == RequestType.SERVER_CHAT)    serverChat(requestDTO);
            else if(requestDTO.getRequestType() == RequestType.MOVE)           move(requestDTO);
            else   empty(requestDTO);
        }


        private void createAccount(RequestDTO requestDTO) {
            String username = requestDTO.getValue1();
            String password = requestDTO.getValue2();

            Optional<User> newUserO = server.dataManager.createNewUser(username, password);
            respondWithUser(newUserO);
            if (newUserO.isPresent()) {
                user = newUserO.get();
                server.showMessage("New USER created: " + user.getUserName());
                isLoggedIn = true;
            }
        }
        private void login(RequestDTO requestDTO) {
            String username = requestDTO.getValue1();
            String password = requestDTO.getValue2();

            Optional<User> newUserO = server.dataManager.getUserIfExist(username, password);
            respondWithUser(newUserO);
            if (newUserO.isPresent()) {
                user = newUserO.get();
                server.showMessage("USER logged in: " + user.getUserName());
                isLoggedIn = true;
            }
        }
        private void serverError(RequestDTO requestDTO) {
            // TODO should probably log / count / record / these - add a feature to access them
            System.out.println("CLIENT COULD NOT PARSE RESPONSE! - " + requestDTO.toString());
            server.showMessage("CLIENT COULD NOT PARSE RESPONSE! - " + requestDTO.toString());
        }
        private void serverChat(RequestDTO requestDTO) {
            server.showMessage("["+user.getUserName()+"] - "+ requestDTO.getValue1());
        }
        private void empty(RequestDTO requestDTO) {
            ResponseDTO responseDTO = new ResponseDTO(ResponseType.BAD, "Why did you send me this: " + requestDTO.toString());
            sendMessage(responseDTO);
        }
        private void move(RequestDTO requestDTO) {
            ResponseDTO responseDTO = new ResponseDTO(ResponseType.BAD, "You Are Not Logged In, and cannot move!");
            if(!isLoggedIn) {
                sendMessage(responseDTO);
            } else {
                Random random = new Random();
                int moved = random.nextInt(6);
                if (requestDTO.getValue1().equals("1")) {
                    responseDTO.setResponseType(ResponseType.GOOD);
                    responseDTO.setValue1(String.valueOf(moved));
                    responseDTO.setValue2(TileType.MONSTER.toString()); // TODO need to keep track of user location
                } else if (requestDTO.getValue1().equals("2")){
                    responseDTO.setResponseType(ResponseType.GOOD);
                    responseDTO.setValue1("-"+String.valueOf(moved));
                    responseDTO.setValue2(TileType.MONSTER.toString());
                } else {
                    sendMessage(responseDTO);
                }
            }
        }
        // todo get BATTLE type, check to make sure user is on monster tile!



        private void respondWithUser(Optional<User> userO) {
            ResponseDTO responseDTO = new ResponseDTO(ResponseType.ERROR);
            if(userO.isPresent()) {
                User newUser = userO.get();
                responseDTO.setResponseType(ResponseType.GOOD);
                responseDTO.setValue1(newUser.getLevelRaw());
                responseDTO.setValue2(newUser.getMoneyRaw());
                responseDTO.setValue3(newUser.getInventoryRaw());
            } else {
                responseDTO.setResponseType(ResponseType.BAD);
            }
            sendMessage(responseDTO);
        }


        private void sendMessage(ResponseDTO responseDTO) {
            try {
                String response = responseDTO.toString();
                System.out.println("RESPONSE SENT: " + response); // TODO add client name to log
                outputStream.writeObject(response);
                outputStream.flush();
                //server.showMessage("\nSERVER - " + message);
            } catch (IOException e) {
                server.chatWindow.append("\n ERROR: DUDE I CANT SEND THAT MESSAGE");
            }
        }
    }

}