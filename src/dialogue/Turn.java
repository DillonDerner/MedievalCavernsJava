package dialogue;

import helpers.PrintHelpers;

/**
 * Created by macbook on 4/8/18.
 */
public class Turn extends PrintHelpers{

    public void ask() {
        println("What what would you like to do: ");
    }

    public void howToPlay() {
        println("How to play");
        println("1 - forward");
        println("2 - backward");
        println("3 - inventory");
    }

    public void youDied() {
        println("You were found unconscious and returned to the floor town");
    }
}
