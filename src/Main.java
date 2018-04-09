import dialogue.Introduction;
import dialogue.Turn;

import java.io.InputStream;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here

        // main game loop

        boolean runGame = true;



        Introduction introduction = new Introduction();
        introduction.welcome();
        introduction.howToPlay();



        Player player = new Player("testPlayer");
        Floor floor1 = new Floor("Noob Plains", 1);

        floor1.about();
        System.out.println(floor1.getTiles().size());


        TurnManager turnManager = new TurnManager(player, floor1);

        while(runGame) {
            turnManager.prompt();

            if(turnManager.isQuit()) runGame = false;
        }


    }


}
