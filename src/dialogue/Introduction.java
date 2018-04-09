package dialogue;

import helpers.PrintHelpers;

/**
 * Created by macbook on 4/8/18.
 */
public class Introduction extends PrintHelpers{

    public void welcome() {
        println("Welcome Player! (fill in more info later)");
    }

    public void howToPlay() {
        println("How to play");
        println("1 - forward");
        println("2 - backward");
        println("3 - inventory");
    }

}


