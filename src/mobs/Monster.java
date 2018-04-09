package mobs;

/**
 * Created by macbook on 4/8/18.
 */
public class Monster {
    private String name;
    private int level;
    private int attack;
    private int defence;
    private int hp;
    private int exp;
    private int coins;

    public Monster() {
    }

    public Monster(String name, int level, int attack, int defence, int hp, int exp, int coins) {
        this.name = name;
        this.level = level;
        this.attack = attack;
        this.defence = defence;
        this.hp = hp;
        this.exp = exp;
        this.coins = coins;
    }

    public void printStats() {
        System.out.println(name + " | lvl: "+level+" | HP: " + hp + " | atk/def: "+attack+"/"+defence);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }
}
