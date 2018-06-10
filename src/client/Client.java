package client;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;
import javax.swing.text.DefaultCaret;

import common.*;
import common.enums.*;


public class Client extends JFrame {

    private static final int PORT = 6789;
    private static final String SERVER_IP = "192.168.50.44";
    //        String localHost = "127.0.0.1";
    //        String remoteHost = "192.168.50.44";

    private JTextField userText;
    private JTextArea chatWindow;

    private User user;
    private boolean interceptorHasValue = false;
    private String interceptorValue = "";

    private UserUtils userUtils = new UserUtils();

    private ResponseDTO responseDTO = new ResponseDTO(ResponseType.BAD);
    private boolean hasServerResponded = false;

    private ClientListener clientListener;

    public boolean connectedToServer = false;
    public boolean loggedIn = false;


    public static void main(String[] args) {
        Client client = new Client();
        client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.startRunning(client);
    }

    public Client() {
        super("Medieval Caverns Login");
        userText = new JTextField();
        userText.setEditable(false);
        userText.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        interceptor(event.getActionCommand());
                        //sendMessage(event.getActionCommand());
                        userText.setText("");
                    }
                }
        );
        add(userText, BorderLayout.SOUTH);
        chatWindow = new JTextArea();
        DefaultCaret caret = (DefaultCaret)chatWindow.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        chatWindow.setFocusable(false);
        chatWindow.setFont(new Font("Book Antiqua", Font.PLAIN, 15));
        add(new JScrollPane(chatWindow), BorderLayout.CENTER);
        setSize(400, 400);
        setVisible(true);
    }


    public void startRunning(Client client) {
        while(true) {
            if(!connectedToServer) {
                showMessage("Welcome to Medieval Caverns!!!");
                try {
                    clientListener = setUpClientListener(client);
                    ableToType(true);
                    mainGameLoop();
                } finally {
                    System.out.println("ya done now!");
                }
            } else {
                System.out.println("HOUSTON WE HAVE A PROBLEM!");
            }
        }
    }
    private ClientListener setUpClientListener(Client client) {
        ClientListener clientListener = new ClientListener(client);
        Thread thread = new Thread(clientListener);
        thread.start();
        return clientListener;
    }

    private void mainGameLoop() {
        login();
        promptHowToPlay();
        String userInput = "";
        do {
            userInput = getNextUserInput();
            if(userInput.startsWith("/server "))sendServerChat(userInput);
            if(userInput.equals("/h"))          showHelp();
            if(userInput.startsWith("/help"))   showHelp();
            if(userInput.startsWith("/h1"))     showHelp1();
            if(userInput.startsWith("/help1"))  showHelp1();
            if(userInput.startsWith("/h2"))     showHelp2();
            if(userInput.startsWith("/help2"))  showHelp2();
            if(userInput.startsWith("/h3"))     showHelp3();
            if(userInput.startsWith("/help3"))  showHelp3();
            if(userInput.startsWith("/h4"))     showHelp4();
            if(userInput.startsWith("/help4"))  showHelp4();
            if(userInput.startsWith("/h5"))     showHelp5();
            if(userInput.startsWith("/help5"))  showHelp5();
            if(userInput.startsWith("/h6"))     showHelp6();
            if(userInput.startsWith("/help6"))  showHelp6();
            if(userInput.equals("1"))           sendMoveRequest(userInput);
            if(userInput.equals("2"))           sendMoveRequest(userInput);


        } while (connectedToServer);
    }


    private void login() {
        showMessage("");
        showMessage("-_-_Please Login_-_-");
        while (!loggedIn && connectedToServer) {
            showMessage("Enter [ 1 ] to Login or [ 2 ] to Create an Account");
            String input = getNextUserInput();
            if (input.equals("1")) {
                showMessage("Username:");
                String username = getNextUserInput();
                showMessage("Password:");
                String password = getNextUserInput();
                showMessage("");

                if (!userUtils.validateString(username + password)) {
                    showMessage("Invalid character typed!");
                } else {
                    showMessage("Checking to see if account exists...");

                    // validate user
                    RequestDTO requestDTO = new RequestDTO(RequestType.LOGIN, username, password);
                    clientListener.sendMessage(requestDTO);
                    ResponseDTO responseDTO = awaitServerResponse();

                    if (responseDTO.getResponseType() == ResponseType.GOOD) {
                        loggedIn = true;
                        user = new User(
                                username,
                                password,
                                Integer.valueOf(responseDTO.getValue1()),
                                Integer.valueOf(responseDTO.getValue2()),
                                userUtils.rawToInventory(responseDTO.getValue3())
                        );
                        showMessage("");
                        showMessage("-_-_Welcome Back [ " + username+ " ]_-_-");
                    } else if (responseDTO.getResponseType() == ResponseType.BAD) {
                        showMessage("incorrect credentials... try again!");
                        showMessage("");
                    } else {
                        showMessage("INVALID RESPONSE FROM SERVER, SORRY!");
                    }
                }
            } else if (input.equals("2")) {
                showMessage("New Username:");
                String username = getNextUserInput();

                showMessage("New Password:");
                String password = getNextUserInput();
                showMessage("");

                if (!userUtils.validateString(username + password)) {
                    showMessage("Invalid character typed!");
                } else {
                    showMessage("Checking to see if account exists...");

                    // create account
                    RequestDTO requestDTO = new RequestDTO(RequestType.CREATE_ACCOUNT, username, password);
                    clientListener.sendMessage(requestDTO);
                    ResponseDTO responseDTO = awaitServerResponse();

                    if (responseDTO.getResponseType() == ResponseType.GOOD) {
                        loggedIn = true;
                        user = new User(
                                username,
                                password,
                                Integer.valueOf(responseDTO.getValue1()),
                                Integer.valueOf(responseDTO.getValue2()),
                                userUtils.rawToInventory(responseDTO.getValue3())
                        );
                        showMessage("");
                        showMessage("Welcome new adventurer! ");
                    } else if (responseDTO.getResponseType() == ResponseType.BAD) {
                        showMessage("This account already exists! did you try logging in?");
                        showMessage("");
                    } else {
                        showMessage("INVALID RESPONSE FROM SERVER, SORRY!");
                    }
                }
            } else {
                showMessage("You entered: [ " + input + " ]... try again little guy...");
                showMessage("");
            }
        }
    }
    private void promptHowToPlay() {
        showMessage("Would you like to learn how to play? [Y/N]");
        if(getValidInput("Yes", "No")) howToPlay();
    }
    private void howToPlay() {
        showMessage("-_-_How To Play_-_-");
        showMessage("-_-_-_-_-_-_-_-_-_-_-");
        showMessage("");
        showMessage("... figure it out!");
        showMessage("ha ha.. seriously tho, if you get stuck");
        showMessage("type /help\n");
    }
    private void showHelp()  {
        showMessage("-_-_-_Help_-_-_-_-");
        showMessage("-_-_-_-_-_-_-_-_-_-");
        showMessage("/h,   /help  - < Help      help menu");
        showMessage("/h1, /help1 - < Commands  help menu");
        showMessage("/h2, /help2 - < Tiles     help menu");
        showMessage("/h3, /help3 - < Moving    help menu");
        showMessage("/h4, /help4 - < Objective help menu");
        showMessage("/h5, /help5 - < Death     help menu");
        showMessage("/h6, /help6 - < Settings  help menu");
        showMessage("");
    }
    private void showHelp1() {
        showMessage("-_-_-_Help 1_-_-_-");
        showMessage("-_-_-_-_-_-_-_-_-_-");
        showMessage("-_-_-_Commands_-_-_-");
        showMessage("/h1, /help1 - - < help menu (this page)");
        showMessage("/p,  /players - < online player list");
        showMessage("/m,  /map - - - < show/hide map");
        showMessage("/s,  /stats - - < show/hide players stats");
        showMessage("/i,  /inventory < show/hide players items");
        showMessage("/c,  /chat  - - < show/hide world chat");
        showMessage("/w,  /whisper - < message player directly");
        showMessage("");
    }
    private void showHelp2() {
        showMessage("-_-_-_Help 2_-_-_-");
        showMessage("-_-_-_-_-_-_-_-_-_-");
        showMessage("-_-_-_Tiles_-_-_-");
        showMessage(" This world is made up of tiles!");
        showMessage("/h2, /help2 - < help menu (this page)");
        showMessage("Towns,  - - - < a safe place to eat drink & buy!");
        showMessage("Monsters, - - < watch out! they are hungry!");
        showMessage("Loot, - - - - < who left this here?");
        showMessage("Resources,  - < you can gather these!");
        showMessage("");
    }
    private void showHelp3() {
        showMessage("-_-_-_Help 3_-_-_-");
        showMessage("-_-_-_-_-_-_-_-_-_-");
        showMessage("-_-_-_Moving_-_-_-");
        showMessage(" Players can move by entering...");
        showMessage(" [ 1 ] - to move forwards");
        showMessage(" [ 2 ] - to move backwards");
        showMessage("/h3, /help3 - - < help menu (this page)");
        showMessage("");
    }
    private void showHelp4() {
        showMessage("-_-_-_Help 4_-_-_-");
        showMessage("-_-_-_-_-_-_-_-_-_-");
        showMessage("-_-_-_Objective_-_-_-");
        showMessage("/h4, /help4 - - < help menu (this page)");
        showMessage("");
    }
    private void showHelp5() {
        showMessage("-_-_-_Help 5_-_-_-");
        showMessage("-_-_-_-_-_-_-_-_-_-");
        showMessage("-_-_-_Death_-_-_-");
        showMessage("/h5, /help5 - - < help menu (this page)");
        showMessage("");
    }
    private void showHelp6() {
        showMessage("-_-_-_Help 6_-_-_-");
        showMessage("-_-_-_-_-_-_-_-_-_-");
        showMessage("-_-_-_Settings_-_-_-");
        showMessage("/h6, /help6 - - < help menu (this page)");
        showMessage("/fs+, /font+ - - < increase window font size");
        showMessage("/server - - < send a message to the server");
        showMessage("");
    }
    private void sendServerChat(String message) {
        message = message.replace("/server ", "");
        RequestDTO requestDTO = new RequestDTO(RequestType.SERVER_CHAT, message);
        clientListener.sendMessage(requestDTO);
    }
    private void sendMoveRequest(String message) {
        RequestDTO requestDTO = new RequestDTO(RequestType.MOVE, message);
        clientListener.sendMessage(requestDTO);

        ResponseDTO responseDTO = awaitServerResponse();
        if(responseDTO.getResponseType() == ResponseType.GOOD) {
            showMessage("You moved [ "+ responseDTO.getValue1()+" ] spaces.");
            if(TileType.valueOf(responseDTO.getValue2()) == TileType.MONSTER) runMonsterTile();
            else if(TileType.valueOf(responseDTO.getValue2()) == TileType.START) runTownTile();
            else if(TileType.valueOf(responseDTO.getValue2()) == TileType.TOWN) runTownTile();
            else if(TileType.valueOf(responseDTO.getValue2()) == TileType.EMPTY) runEmptyTile();
            else if(TileType.valueOf(responseDTO.getValue2()) == TileType.FORREST) runForrestTile();
            else if(TileType.valueOf(responseDTO.getValue2()) == TileType.POND) runPondTile();
            else if(TileType.valueOf(responseDTO.getValue2()) == TileType.LOOT) runLootTile();
            else if(TileType.valueOf(responseDTO.getValue2()) == TileType.END) runEndTile();
            else {
                showMessage("YIKES...I do not recognize that tile type...");
            }

        }
        else if(responseDTO.getResponseType() == ResponseType.BAD) {
            if(message.equals("1")) {
                showMessage("You cannot move Forward anymore.");
            } else {
                showMessage("You cannot move Backward anymore.");
            }
        }
        else{
            showMessage("YIKES...Something when wrong with server movement response");
        }
    }

    private void runMonsterTile() {
        showMessage(responseDTO.getValue3());
        showMessage("what would you like to do F/R");
        String userInput = getNextUserInput();
        userInput = userInput.toLowerCase();
        if(userInput.equals("f") || userInput.equals("fight")){
            //send fight request
        } else if (userInput.equals("r") || userInput.equals("run")){
            // send run request
        } else {

        }
    }
    private void runTownTile() {
        showMessage(responseDTO.getValue3());
        showMessage("what would you like to do F/R");
        String userInput = getNextUserInput();
        userInput = userInput.toLowerCase();
        if(userInput.equals("f") || userInput.equals("fight")){
            //send fight request
        } else if (userInput.equals("r") || userInput.equals("run")){
            // send run request
        } else {

        }
    }
    private void runEmptyTile() {
        showMessage(responseDTO.getValue3());
        showMessage("what would you like to do F/R");
        String userInput = getNextUserInput();
        userInput = userInput.toLowerCase();
        if(userInput.equals("f") || userInput.equals("fight")){
            //send fight request
        } else if (userInput.equals("r") || userInput.equals("run")){
            // send run request
        } else {

        }
    }
    private void runForrestTile() {
        showMessage(responseDTO.getValue3());
        showMessage("what would you like to do F/R");
        String userInput = getNextUserInput();
        userInput = userInput.toLowerCase();
        if(userInput.equals("f") || userInput.equals("fight")){
            //send fight request
        } else if (userInput.equals("r") || userInput.equals("run")){
            // send run request
        } else {

        }
    }
    private void runPondTile() {
        showMessage(responseDTO.getValue3());
        showMessage("what would you like to do F/R");
        String userInput = getNextUserInput();
        userInput = userInput.toLowerCase();
        if(userInput.equals("f") || userInput.equals("fight")){
            //send fight request
        } else if (userInput.equals("r") || userInput.equals("run")){
            // send run request
        } else {

        }
    }
    private void runLootTile() {
        showMessage(responseDTO.getValue3());
        showMessage("what would you like to do F/R");
        String userInput = getNextUserInput();
        userInput = userInput.toLowerCase();
        if(userInput.equals("f") || userInput.equals("fight")){
            //send fight request
        } else if (userInput.equals("r") || userInput.equals("run")){
            // send run request
        } else {

        }
    }
    private void runEndTile() {
        showMessage(responseDTO.getValue3());
        showMessage("what would you like to do F/R");
        String userInput = getNextUserInput();
        userInput = userInput.toLowerCase();
        if(userInput.equals("f") || userInput.equals("fight")){
            //send fight request
        } else if (userInput.equals("r") || userInput.equals("run")){
            // send run request
        } else {

        }
    }


    private boolean getNextInputYesOrNo() {
        String input = getNextUserInput();
        showMessage("");
        input = input.toLowerCase();
        return (input.equals("y") || input.equals("yes"));
    }
    private boolean getValidInput(String trueString, String falseString) {
        boolean found = false;
        boolean answer = false;
        while(!found) {
            String input = getNextUserInput();
            showMessage("");
            String inputLower = input.toLowerCase();
            String trueStringLower = trueString.toLowerCase();
            String falseStringLower = falseString.toLowerCase();
            String trueFirstLower = trueStringLower.substring(0,1);
            String falseFirstLower = falseStringLower.substring(0,1);
            String trueFirst = trueString.substring(0,1);
            String falseFirst = falseString.substring(0,1);
            if(inputLower.equals(trueFirstLower) || inputLower.equals(trueStringLower)) {
                found = true;
                answer = true;
            } else if (inputLower.equals(falseFirstLower) || inputLower.equals(falseStringLower)) {
                found = true;
                answer = false;
            } else {
                showMessage("What is [ " + input + " ]?, enter [ "+ trueFirst +" ] to "+ trueString +" or [ "+ falseFirst +" ] to "+ falseString);
            }
        }
        return answer;
    }



    private String getNextUserInput() {
        while (!interceptorHasValue) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                System.out.println("Issue waiting for input!");
                e.printStackTrace();
            }
        }
        interceptorHasValue = false;
        String temp = interceptorValue;
        interceptorValue = "";
        showMessage("You: " + temp);
        return temp;
    }
    private ResponseDTO awaitServerResponse() {
        String message = "[ERROR,,,,,]";
        try {
            do {
                Thread.sleep(100);
            } while (!hasServerResponded);
            hasServerResponded = false;
            ResponseDTO temp = responseDTO;
            responseDTO = new ResponseDTO(ResponseType.BAD);
            return temp;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return responseDTO;
    }
    private void showMessage(final String text) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        chatWindow.append("\n" + text);
                    }
                }
        );
    }
    private void showSelfMessage(final String text) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        if (user != null) chatWindow.append("\n" + user.getUserName() + ": " + text);
                        else chatWindow.append("\n" + "You: " + text);
                    }
                }
        );

    }


    private ResponseDTO toResponseDTO(String message) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setResponseType(ResponseType.ERROR);
        System.out.println("RECEIVED: " + message);
        if (!message.startsWith("[")) {
            System.out.println("SERVER sent a bad response: " + message);
            RequestDTO requestDTO = new RequestDTO(RequestType.SERVER_ERROR, "The SERVER sent me an Invalid Response Format: " + message);
            clientListener.sendMessage(requestDTO);
        } else {
            message = message.replace("[", "");
            message = message.replace("]", "");
            List<String> responseList = Arrays.asList(message.split(","));
            try {
                ResponseType responseType = ResponseType.valueOf(responseList.get(0));
                responseDTO.setResponseType(responseType);
                if (responseList.size() >= 2) responseDTO.setValue1(responseList.get(1));
                if (responseList.size() >= 3) responseDTO.setValue2(responseList.get(2));
                if (responseList.size() >= 4) responseDTO.setValue3(responseList.get(3));
                if (responseList.size() >= 5) responseDTO.setValue4(responseList.get(4));
                if (responseList.size() >= 6) responseDTO.setValue5(responseList.get(5));

            } catch (Exception e) {
                System.out.println("SERVER sent a bad responseType: " + message);
                RequestDTO requestDTO = new RequestDTO(RequestType.SERVER_ERROR, "The SERVER sent a bad responseType: " + message);
                clientListener.sendMessage(requestDTO);
            }
        }
        return responseDTO;
    }
    private void interceptor(String message) {
        interceptorValue = message;
        interceptorHasValue = true;
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


    public static class ClientListener implements Runnable {
        private Client client;
        private Socket connection;
        private ObjectInputStream inputStream;
        private ObjectOutputStream outputStream;


        ClientListener(Client client) {
            this.client = client;
            try {
                connectToServer();  //this.connection = connection;
                setUpStreams();
                client.connectedToServer = true;//this.inputStream = inputStream;
            } catch (IOException e) { //this.outputStream = outputStream;
                System.out.println("Issue Connecting to Server from Client thread OR");
                System.out.println("Issue Setting up Client thread streams");
                e.printStackTrace();
            }
        }


        public void run() {
            System.out.println("ClientListener Thread Started!");
            String serverMessage = "null";
            while (!serverMessage.startsWith("[DISCONNECT")) {
                try {
                    serverMessage = (String) inputStream.readObject();
                    ResponseDTO responseDTO = client.toResponseDTO(serverMessage);
                    if (responseDTO.getResponseType() == ResponseType.SERVER) {
                        client.showMessage("SERVER - " + responseDTO.getValue1());
                    } else {
                        client.responseDTO = responseDTO;
                        client.hasServerResponded = true;
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    System.out.println("Dude I cant read that" + serverMessage);
                } catch (IOException e) {
                    System.out.println("ClientListener had an issue with that message: " + serverMessage);
                    e.printStackTrace();
                    serverMessage = "[DISCONNECT,,,,,]";
                }
            }
            closeCrap();
            System.out.println("ClientListener Thread Ended!");
        }
        private void connectToServer() throws IOException {
            boolean serverUp = false;
            //showMessage("Attempting to connect to Server...");
            while (!serverUp) {
                try {
                    connection = new Socket(InetAddress.getByName(SERVER_IP), PORT);
                    serverUp = true;
                    client.showMessage("Server Status: UP");
                } catch (IOException e) {
                    try {
                        client.showMessage("Server Status: DOWN");
                        Thread.sleep(10000);
                    } catch (InterruptedException e1) {
                        System.out.println("Issue Sleeping: " + e.getMessage());
                    }
                }
            }
        }
        private ObjectInputStream setUpStreams() throws IOException {
            outputStream = new ObjectOutputStream(connection.getOutputStream());
            outputStream.flush();
            inputStream = new ObjectInputStream(connection.getInputStream());
            client.showMessage("Socket Streams: CONNECTED");
            return inputStream;
        }
        private void closeCrap() {
            client.showMessage("Server Status: DOWN");
            client.showMessage("Closing connection to SERVER ");
            //client.ableToType(false);
            try {
                outputStream.close();
                inputStream.close();
                connection.close();
                client.connectedToServer = false;
                client.loggedIn = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public void sendMessage(RequestDTO requestDTO) {
            try {
                String request = requestDTO.toString();
                System.out.println("REQUEST SENT: " + request);
                outputStream.writeObject(request);
                outputStream.flush();
                //showMessage("CLIENT - " + message);
            } catch (IOException e) {
                System.out.println("Connection to SERVER has been lost. Cannot send message.");
                client.responseDTO = new ResponseDTO(ResponseType.DISCONNECT, "Connection to SERVER has been lost.");
                client.hasServerResponded = true;
            }
        }
    }

}
