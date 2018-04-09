import helpers.Dice;
import mobs.Monster;

/**
 * Created by macbook on 4/8/18.
 */
public class BattleManager {
    private Dice dice;

    public BattleManager() {
        dice = new Dice();
    }

    public void fight(Player player, Monster monster) {
        player.printStats();


        ///TODO start here!

    }
}
