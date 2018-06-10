package common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserUtils {

    public UserUtils() {
    }

    public Map<String, Integer> rawToInventory(String rawInventory) {
        Map<String, Integer> inventory = new HashMap<>();
        try {
            rawInventory = rawInventory.replace("[", "");
            rawInventory = rawInventory.replace("]", "");

            List<String> inventoryList = Arrays.asList(rawInventory.split(";"));
            for (String item : inventoryList) {
                try {
                    List<String> itemSplit = Arrays.asList(item.split("="));
                    inventory.put(itemSplit.get(0), Integer.parseInt(itemSplit.get(1)));
                } catch (Exception e) {
                    System.out.println("ERROR! Could not parse rawItem: " + item);
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR! Could not parse rawInventory into Map<String, Integer> : " + rawInventory);
        }
        return inventory;
    }

    public boolean validateString(String userNameOrPassword) {
        if(userNameOrPassword.equals(""))    return false;
        if(userNameOrPassword.contains(",")) return false;
        if(userNameOrPassword.contains("[")) return false;
        if(userNameOrPassword.contains("]")) return false;
        if(userNameOrPassword.contains(";")) return false;
        if(userNameOrPassword.contains("=")) return false;
        return true;
    }

}
