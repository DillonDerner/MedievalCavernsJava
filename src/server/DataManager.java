package server;

import common.User;
import common.UserUtils;

import java.io.*;
import java.util.*;

public class DataManager {

    private UserUtils userUtils = new UserUtils();

    public DataManager() {
    }

    public Optional<User> createNewUser(String username, String password) {
        if (!userUtils.validateString(username+password)) {
            return Optional.empty();
        }
        if(!doesUserExists(username)) {
            User newUser =
                    new User(
                            getNextUserId(),
                            username,
                            password,
                            3,
                            0,
                            userUtils.rawToInventory("[bread=1;fish=1;wood=1]")
                    );
            updateUser(newUser);
            return Optional.of(newUser);
        }
        return Optional.empty();
    }

    public Optional<User> getUserIfExist(String username, String password) {
        if (!userUtils.validateString(username+password)) {
            return Optional.empty();
        }
        List<User> users = getUsersFromFile();
        for (User user : users) {
            if(user.getUserName().equals(username) && user.getPassword().equals(password)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public void updateUser(User user) {
        List<String> users = readRawFromUserFile();
        if (users.size() <= user.getUserId()) users.add(""); // handles new users
        users.set(user.getUserId(), user.toRaw());
        writeRawToUserFile(users);
    }

    private boolean doesUserExists(String username) {
        List<User> users = getUsersFromFile();
        for (User user : users) {
            if(user.getUserName().equals(username)) {
                //this.user = user;
                return true;
            }
        }
        return false;
    }



    private Optional<User> recordToUser(String record) {
        List<String> userdata = Arrays.asList(record.split(","));
        try {
            Map<String, Integer> inventory = userUtils.rawToInventory(userdata.get(5));
            User user = new User(
                    Integer.parseInt(userdata.get(0)),
                    userdata.get(1),
                    userdata.get(2),
                    Integer.parseInt(userdata.get(3)),
                    Integer.parseInt(userdata.get(4)),
                    inventory
            );

            return Optional.of(user);
        } catch (Exception e) {
            System.out.println("SERVER ERROR! Could not parse UserData: " + userdata.get(0) + " from save file!");
        }
        return Optional.empty();
    }

    private List<User> getUsersFromFile() {
        List<String> userRecords = readRawFromUserFile();
        List<User> userList = new ArrayList<>();
        boolean first = true;
        for (String record : userRecords) {
            if (first) {
                first = false;
            } else {
                recordToUser(record).ifPresent(userList::add);
            }
        }
        return userList;
    }

    private int getNextUserId(){
        return readRawFromUserFile().size();
    }

    private List<String> readRawFromUserFile() {
        String filePath = "C:\\Users\\Dillon\\IdeaProjects\\MedievalCavernsJava\\src\\server\\data\\UserData";
        List<String> userRecords = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            while (line != null) {
                userRecords.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch(Exception e) {
            System.out.println("Could not locate the file " + filePath);
            e.printStackTrace();
        }
        return userRecords;
    }

    private void writeRawToUserFile(List<String> lines) {
        String filePath = "C:\\Users\\Dillon\\IdeaProjects\\MedievalCavernsJava\\src\\server\\data\\UserData";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            int counter = 0;
            for (String line : lines) {
                counter ++;
                writer.write(line);
                if (counter != lines.size()) writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
