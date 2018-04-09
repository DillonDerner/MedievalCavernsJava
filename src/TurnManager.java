import dialogue.Turn;
import enums.GeneralAction;
import helpers.Dice;

import java.util.Scanner;

/**
 * Created by macbook on 4/8/18.
 */
public class TurnManager {
    private Player player;
    private Floor floor;

    private boolean quit = false;

    private Turn turnDialogue = new Turn();
    private TileManager tileManager = new TileManager();
    private Dice dice = new Dice();
    private Scanner scanner = new Scanner(System.in);

    public TurnManager(Player player, Floor floor) {
        this.player = player;
        this.floor = floor;
    }

    public boolean isQuit() {
        return quit;
    }

    public void prompt() {
        turnDialogue.ask();
        String input = scanner.next();
        GeneralAction action = decryptInput(input);

        int roll = dice.roll();

        boolean moved = false;

        if(action == GeneralAction.QUIT) return;
        if(action == GeneralAction.UNKNOWN) turnDialogue.howToPlay();
        if(action == GeneralAction.FORWARD) {
            player.move(roll);
            moved = true;
        }
        if(action == GeneralAction.BACKWARD) {
            player.move(-roll);
            moved = true;
        }

        if (moved) {
            int location = player.getLocation();
            Tile playersTile = floor.getTile(location);
            playersTile.about();

            tileManager.runPlayerOnTile(player, playersTile);

        }
    }

    private GeneralAction decryptInput(String input) {
        if (input.equals("1"))
            return GeneralAction.FORWARD;
        if (input.equals("2"))
            return GeneralAction.BACKWARD;
        if (input.equals("3"))
            return GeneralAction.INVENTORY;
        if (input.equals("q")) {
            quit = true;
            return GeneralAction.QUIT;
        }
        else
            return GeneralAction.UNKNOWN;
    }


}
