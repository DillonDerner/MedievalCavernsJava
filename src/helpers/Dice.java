package helpers;

import java.util.Random;

/**
 * Created by macbook on 4/8/18.
 */
public class Dice {

    private Random random = new Random();

    public int roll() {
        return Math.abs(random.nextInt() % 6) + 1; // 1-6
    }

    public int roll(int highestNumber) {
        return Math.abs(random.nextInt() % highestNumber) + 1; // 1-highestNumber
    }

}
