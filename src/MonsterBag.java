import helpers.Dice;
import mobs.Monster;
import mobs.Rat;

/**
 * Created by macbook on 4/8/18.
 */
public class MonsterBag {

    private Dice dice = new Dice();

    public MonsterBag() {
    }

    public Monster grab(int floor) {
        Monster monster = new Rat(); // default
        if (floor ==1 ) {
            monster = new Rat();
        }

        // future floors will use dice to decide

        return monster;
    }
}
