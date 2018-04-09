/**
 * Created by macbook on 4/8/18.
 */
public class Player {
    private String name;
    private int floor = 1;
    private int location = 1;

    private int level = 1;
    private int hp = 10;
    private int power = 1;
    private int defence = 1;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public int move(int amount) {
        System.out.println("c loc:"+ location);
        location += amount;

        if(location < 1) location = 1;
        if(location > 50)location = 50;

        System.out.println("n loc:"+ location);
        return location;
    }

    public void printStats() {
        System.out.println(name + " | lvl: "+level+" | HP: " + hp + " | atk/def: "+power+"/"+defence);
    }
}
