package common;

import java.util.HashMap;
import java.util.Map;

public class User {
    //## number, username, password, level, money, items
    private int userId;
    private String userName;
    private String password;
    private int level;
    private int money;
    private Map<String, Integer> inventory = new HashMap<String, Integer>();


    public User(int userId, String userName, String password, int level, int money, Map<String, Integer> inventory) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.level = level;
        this.money = money;
        this.inventory = inventory;
    }

    public User(String userName, String password, int level, int money, Map<String, Integer> inventory) {
        this.userName = userName;
        this.password = password;
        this.level = level;
        this.money = money;
        this.inventory = inventory;
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }


    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLevel() {
        return level;
    }

    public String getLevelRaw() {
        return Integer.toString(level);
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMoney() {
        return money;
    }

    public String getMoneyRaw() {
        return Integer.toString(money);
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public String getInventoryRaw() {
        String inventoryRaw = "[";
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            inventoryRaw += entry.getKey() + "=" + entry.getValue() + ";";
        }
        inventoryRaw = inventoryRaw.substring(0, inventoryRaw.length() - 1); // remove last ;
        inventoryRaw += "]";

        return inventoryRaw;
    }

    public void setInventory(Map<String, Integer> inventory) {
        this.inventory = inventory;
    }


    public String toRaw() {
        return  userId   +","+
                userName +","+
                password +","+
                level    +","+
                money    +","+
                getInventoryRaw();
    }

}
